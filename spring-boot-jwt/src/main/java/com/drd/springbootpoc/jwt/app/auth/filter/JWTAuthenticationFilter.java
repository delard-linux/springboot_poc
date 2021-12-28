package com.drd.springbootpoc.jwt.app.auth.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.drd.springbootpoc.jwt.app.model.entity.security.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	//se manda el user/pwd usando form-data (request)
	
	private AuthenticationManager authenticationManager;
	
	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		var username = obtainUsername(request);
		var password = obtainPassword(request);
		
		if (username!=null || password!=null) {
			// caso (form-data)
			logger.info("Username desde request parameter (form-data)" + username);
			logger.info("Password desde request parameter (form-data)" + password);
		} else {
			// caso (raw)
			try {
				Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
				username = user.getUsername();
				password = user.getPassword();
				logger.info("Username desde request InputStream (raw)" + username);
				logger.info("Password desde request InputStream (raw)" + password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		username = (username != null) ? username.trim() : username;
		
		var authToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((User)authResult.getPrincipal()).getUsername();
		
		var roles = authResult.getAuthorities();
		
		Claims claims =  Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		String secretKeyString = new String(SECRET_KEY.getEncoded(), StandardCharsets.UTF_16);
		logger.info("SecretKey: " + secretKeyString);
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.signWith(SECRET_KEY)
				.setIssuedAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(new Date(System.currentTimeMillis()+3600000L*4))
				.compact();
		
		response.addHeader("Authorization","Bearer " + token);
		
		Map<String, Object> body = new HashMap<>();
		
		body.put("token", token);
		body.put("user", authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesion con éxito", username));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

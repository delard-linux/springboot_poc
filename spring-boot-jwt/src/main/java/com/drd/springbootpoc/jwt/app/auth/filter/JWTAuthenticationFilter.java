package com.drd.springbootpoc.jwt.app.auth.filter;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	//se manda el user/pwd usando form-data (request)
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		var username = obtainUsername(request);
		username = (username != null) ? username : "";
		var password = obtainPassword(request);
		password = (password != null) ? password : "";
		
		logger.info("Username desde request parameter (form-data)" + username);
		logger.info("Password desde request parameter (form-data)" + password);
		
		username = username.trim();


		var authToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((User)authResult.getPrincipal()).getUsername();

		
		String token = Jwts.builder()
						.setSubject(username)
						.signWith(SignatureAlgorithm.HS512,
								("Alguna.Clave.Secreta.12346464646464656"
								+ "123464646464646561234646464646465612346464646464656123464646464646561234646464646465612346"
								+ "464646464656123464646464646561234646464646465612346464646464656123464646464646561234646464"
								+ "646465612346464646464656123464646464646561234646464646465612346464646464656123464646464646"
								+ "561234646464646465612346464646464656123464646464646561234646464646465612346464646464656123"
								+ "464646464646561234646464646465612346464646464656123464646464646561234646464646465612346464"
								+ "64646465612346464646464656123464646464646561234646464646465612346464646464656")
								.getBytes())
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

package com.drd.springbootpoc.jwt.app.auth.service;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.drd.springbootpoc.jwt.app.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImpl implements IJWTService {

	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	public static final long EXPIRATION_DATE = 14000000L;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String KEY_AUTHORITIES = "authorities";
	
	@Override
	public String create(Authentication authentication) throws IOException {
		String username = ((User)authentication.getPrincipal()).getUsername();
		
		var roles = authentication.getAuthorities();
		
		Claims claims =  Jwts.claims();
		claims.put(KEY_AUTHORITIES, new ObjectMapper().writeValueAsString(roles));
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.signWith(SECRET_KEY)
				.setIssuedAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_DATE))
				.compact();
	}

	@Override
	public boolean validate(String token) {

		boolean validoToken;

		try {
			getClaims(token);
			validoToken = true;
		} catch (JwtException e) {
			e.printStackTrace();
			validoToken = false;
		}
		
		return validoToken;
	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY).build()
				.parseClaimsJws(resolveToken(token))
				.getBody();
	}

	@Override
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get(KEY_AUTHORITIES);
		
		return Arrays.asList(new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
	}

	@Override
	public String resolveToken(String token) {
		if(token!=null && token.startsWith(TOKEN_PREFIX))
			return token.replace(TOKEN_PREFIX, "");
		return null;
	}

}

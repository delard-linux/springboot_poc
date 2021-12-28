package com.drd.springbootpoc.jwt.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.drd.springbootpoc.jwt.app.auth.service.IJWTService;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	IJWTService jwtservice;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, IJWTService jwtservice) {
		super(authenticationManager);
		this.jwtservice = jwtservice;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = null;

		if (jwtservice.validate(header)) {
			authentication = new UsernamePasswordAuthenticationToken(jwtservice.getUsername(header), null, jwtservice.getRoles(header));
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	protected boolean requiresAuthentication(String header) {
		return !(header == null || !header.startsWith("Bearer"));
	}

}


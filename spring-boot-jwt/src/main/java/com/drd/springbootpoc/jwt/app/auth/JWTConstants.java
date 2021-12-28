package com.drd.springbootpoc.jwt.app.auth;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTConstants {
	
	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	private JWTConstants() {}
	
}

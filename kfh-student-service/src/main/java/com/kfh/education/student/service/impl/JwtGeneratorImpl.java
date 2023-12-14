package com.kfh.education.student.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kfh.education.student.service.JwtGeneratorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtGeneratorImpl implements JwtGeneratorService {
	private final Logger log = LoggerFactory.getLogger(JwtGeneratorImpl.class);
	@Value("${jwt.secret}")
	private String secret;
	@Value("${app.jwttoken.message}")
	private String message;

	@Override
	public Map<String, String> generateToken(String userName) {
		String jwtToken = "";
		jwtToken = Jwts.builder().setSubject(userName).setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secret").compact();
		Map<String, String> jwtTokenGen = new HashMap<>();
		jwtTokenGen.put("token", jwtToken);
		jwtTokenGen.put("message", message);
		return jwtTokenGen;
	}

	@Override
	public String getUserNameFromToken(String token) {
		if (token.contains("Bearer")) {
			token = token.substring(7);
		}
		return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();
	}
}
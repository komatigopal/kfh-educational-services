package com.kfh.education.courses.service;

import java.util.Map;

public interface JwtGeneratorService {
	Map<String, String> generateToken(String userName);

	public String getUserNameFromToken(String token);
}

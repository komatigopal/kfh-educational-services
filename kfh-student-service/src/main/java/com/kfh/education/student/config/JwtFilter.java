package com.kfh.education.student.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private final Logger log = LoggerFactory.getLogger(JwtFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		final String authHeader = request.getHeader("Authorization");
		log.info("authHeader - {}", authHeader);
		final String token = authHeader.substring(7);
		String userName = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();
		log.info("userName - {}", userName);
		if (!userName.equals("admin")) {
			if (request.getRequestURI().contains("admin")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
		}
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(request, response);
		} else {
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
		}
		Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
		request.setAttribute("claims", claims);
		request.setAttribute("blog", request.getParameter("id"));
		filterChain.doFilter(request, response);
	}
}
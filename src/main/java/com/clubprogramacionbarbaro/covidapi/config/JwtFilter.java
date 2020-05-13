package com.clubprogramacionbarbaro.covidapi.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtFilter extends GenericFilterBean{

	private final String secretKey;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String authorizationHeader = httpRequest.getHeader("Authorization");
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String accessToken = authorizationHeader.substring(7);
			
			try {
				Claims claims = Jwts.parser()
						.setSigningKey(secretKey)
						.parseClaimsJws(accessToken)
						.getBody();
				
				httpRequest.setAttribute("claims", claims);
			} catch (SignatureException e) {
				throw new ServletException("Invalid token");
			}
		}else {
			throw new ServletException("Missing or invalid Authorization Bearer");
		}
		
		chain.doFilter(httpRequest, httpResponse);
	}
}

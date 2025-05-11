package com.wcw.backend.config;

import com.wcw.backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private Set<String> excludeUrlSet = new HashSet<>();

	@Override
	public void initFilterBean() {
		FilterConfig filterConfig = getFilterConfig();
		if (filterConfig != null) {
			String excludeUrls = filterConfig.getInitParameter("excludeUrls");
			if (excludeUrls != null) {
				excludeUrlSet.addAll(Arrays.asList(excludeUrls.split(",")));
			}
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		// 登录、注册和免认证接口不拦截
		if ("/api/login".equals(path) || "/api/register".equals(path) || excludeUrlSet.contains(path)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		if (token == null || !JwtUtil.verify(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"code\":401,\"msg\":\"Invalid or missing token\"}");
			return;
		}
		Long userId = JwtUtil.getUserId(token);
		if (userId == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"code\":401,\"msg\":\"Invalid token userId\"}");
			return;
		}
		String redisKey = "login:token:" + userId;
		String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
		if (redisToken == null || !redisToken.equals(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"code\":401,\"msg\":\"Token expired or not found in Redis\"}");
			return;
		}
		filterChain.doFilter(request, response);
	}
}

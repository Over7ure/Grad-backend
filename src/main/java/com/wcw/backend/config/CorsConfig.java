package com.wcw.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS配置类，用于处理跨域资源共享
 */
@Configuration
public class CorsConfig {
	// 设置CORS预检请求的有效期为24小时，减少预检请求次数
	private static final long MAX_AGE = 24 * 60 * 60;

	/**
	 * 创建CorsFilter Bean，配置CORS策略
	 * 
	 * @return 配置好的CorsFilter实例
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("http://localhost:8080"); // 仅允许本地前端访问
		corsConfiguration.addAllowedHeader("*");                     // 允许所有请求头
		corsConfiguration.addAllowedMethod("*");                     // 允许所有HTTP方法
		corsConfiguration.setAllowCredentials(true);                 // 允许携带cookie和认证信息
		corsConfiguration.setMaxAge(MAX_AGE);                        // 设置预检请求的缓存时间
		source.registerCorsConfiguration("/**", corsConfiguration);  // 对所有路径应用此配置
		return new CorsFilter(source);
	}
}

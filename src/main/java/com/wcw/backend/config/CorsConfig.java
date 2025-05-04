package com.wcw.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * CORS配置类，用于处理跨域资源共享
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	// 设置CORS预检请求的有效期为24小时，使用Duration类型
	private static final Duration MAX_AGE = Duration.ofHours(24);

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("http://localhost:8080") // 只允许前端本地地址访问
				.allowedHeaders("*")         // 允许所有Header
				.allowedMethods("*")         // 允许所有Method
				.allowCredentials(true)      // 允许携带cookie和认证信息
				.maxAge(MAX_AGE.getSeconds()); // 设置预检请求的缓存时间（秒）
	}
}



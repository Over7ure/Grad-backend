package com.wcw.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class JwtUtil {
	private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000L;
	private static JwtUtil instance;
	@Value("${jwt.secret}")
	private String secret;

	/**
	 * 生成token
	 *
	 * @param userId 用户ID
	 * @return token字符串
	 */
	public static String createToken(Long userId) {
		Date now = new Date();
		Date expire = new Date(now.getTime() + EXPIRE_TIME);
		return JWT.create()
				.withClaim("userId", userId)
				.withIssuedAt(now)
				.withExpiresAt(expire)
				.sign(Algorithm.HMAC256(instance.secret));
	}

	/**
	 * 校验token
	 *
	 * @param token token字符串
	 * @return 是否有效
	 */
	public static boolean verify(String token) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(instance.secret)).build();
			verifier.verify(token);
			return true;
		} catch (JWTVerificationException e) {
			return false;
		}
	}

	/**
	 * 从token中获取userId
	 */
	public static Long getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userId").asLong();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	@PostConstruct
	public void init() {
		instance = this;
	}
}

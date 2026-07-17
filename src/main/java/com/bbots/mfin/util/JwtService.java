package com.bbots.mfin.util;
 
import java.security.Key;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
 
@Service
public class JwtService {
 
	@Value("${jwt.secret:bbots_ams_secret_key_2026_very_secure_key_12345}")
	private String secretKey;
 
	@Value("${jwt.mother.expiration:3600000}")
	private long motherTokenExpiration;
 
	@Value("${jwt.child.expiration:3600000}")
	private long childTokenExpiration;
 
	@Value("${jwt.refresh.expiration:604800000}")
	private long refreshExpiration;
 
	private Key getSignInKey() {
		byte[] keyBytes = secretKey.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}
 
	public String generateMainToken(UserDto user, Integer product) {
		return buildToken(user, motherTokenExpiration, "mother", product);
	}
 
	public String generateChildToken(UserDto user, Integer product) {
		return buildToken(user, childTokenExpiration, "child", product);
	}
 
	private String buildToken(UserDto user, long expiration, String tokenType, Integer product) {
 
		Map<String, Object> claims = new HashMap<>();
 
		claims.put("orgCode", user.getOrgCode());
 
		if (product != null)
			claims.put("prodCode", product);
		else
			claims.put("prodCode", "AccessManager");
 
		claims.put("token-type", tokenType);
		claims.put("userId", user.getEmail());
		claims.put("userscd", user.getUserScd());
 
		if ("child".equals(tokenType)) {
			claims.put("orgName", user.getOrgName());
			claims.put("orglogo", user.getOrgLogo());
			claims.put("orgmodtime", user.getOrgmodDate() != null ? user.getOrgmodDate().toString() : "");
			claims.put("userlogo", user.getOrgLogo());
			claims.put("usermodtime", user.getOrgmodDate() != null ? user.getOrgmodDate().toString() : "");
			claims.put("roleType", user.getRoleType());
			claims.put("accessCode", user.getAccessCd());
		}
 
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(600);
 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
				.withZone(ZoneId.of("Asia/Kolkata"));
 
		String iatReadable = formatter.format(now);
		String expReadable = formatter.format(expiry);
		return Jwts.builder().setClaims(claims)
				.setIssuer("AM").setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
				.compact();
	}
 
	public String extractUsername(String token) {
		return extractClaim(token, claims -> claims.get("userId", String.class));
	}
	public String extractOrgModTime(String token) {
		return extractClaim(token, claims -> claims.get("orgmodtime", String.class));
	}
 
	public String extractUserModTime(String token) {
		return extractClaim(token, claims -> claims.get("usermodtime", String.class));
	}
	public Integer extractProductCode(String token) {
		return extractClaim(token, claims -> claims.get("prodCode", Integer.class));
	}
 
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
 
	public String extractTokenType(String token) {
		return extractClaim(token, claims -> claims.get("token-type", String.class));
	}
 
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}
 
	public boolean isTokenValid(String token, String userEmail) {
		final String username = extractUsername(token);
		return (username.equals(userEmail)) && !isTokenExpired(token);
	}
 
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
 
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
 
	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
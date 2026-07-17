package com.bbots.mfin.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bbots.mfin.dto.CustomUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        log.debug("JWT filter processing request: {}", request.getServletPath());
        String path = request.getServletPath();

        // Skip JWT filter for public / auth / token-exchange endpoints
        if (path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.contains("swagger") ||
                path.startsWith("/auth") ||
                path.startsWith("/exchange") ||
                path.equals("/api/auth/login") ||
                path.equals("/api/auth/queue") ||
                path.equals("/accessmanager/auth/login")) {
            log.debug("Skipping JWT filter for path: {}", path);
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // Extract username — 'sub' or fallback to 'userId'
                String username = claims.getSubject();
                if (username == null) {
                    username = claims.get("userId", String.class);
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Extract orgCode from JWT claims
                    Long orgCode = null;
                    Object orgCodeObj = claims.get("orgCode");
                    if (orgCodeObj instanceof Number) {
                        orgCode = ((Number) orgCodeObj).longValue();
                    }

                    // Extract userscd (stored as String in token)
                    Long userScd = null;
                    String userScdStr = claims.get("userscd", String.class);
                    if (userScdStr != null) {
                        try {
                            userScd = Long.parseLong(userScdStr);
                        } catch (NumberFormatException ignored) {}
                    }

                    // Extract roleType (only present in child tokens)
                    String roleType = claims.get("roleType", String.class);

                    // Build rich CustomUserDetails so controllers can read role / org
                    CustomUserDetails userDetails = new CustomUserDetails(
                            username,
                            "",
                            new ArrayList<>(),
                            userScd,
                            orgCode,
                            roleType,
                            userScdStr
                    );

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("User {} authenticated (orgCode={}, roleType={})", username, orgCode, roleType);
                }

            } catch (Exception e) {
                log.warn("Invalid or expired JWT token: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

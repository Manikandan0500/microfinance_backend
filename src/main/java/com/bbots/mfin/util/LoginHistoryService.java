package com.bbots.mfin.util;
 
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.LoginHistoryDto;
import com.bbots.mfin.repository.LoginHistoryRepository;
import com.bbots.mfin.repository.ProductRepository;
import com.bbots.mfin.service.DeviceInfoService;
import com.bbots.mfin.service.GeoLocationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
 
@Service
public class LoginHistoryService {
 
    private final LoginHistoryRepository loginHistoryRepository;
    private final ProductRepository productRepository;
    private final GeoLocationService geoLocationService;
    private final DeviceInfoService deviceInfoService;
    @Value("${jwt.secret:bbots_ams_secret_key_2026_very_secure_key_12345}")
    private String jwtSecret;
 
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
 
    public LoginHistoryService(LoginHistoryRepository loginHistoryRepository,
            ProductRepository productRepository,
            GeoLocationService geoLocationService,
            DeviceInfoService deviceInfoService) {
        this.loginHistoryRepository = loginHistoryRepository;
        this.productRepository = productRepository;
        this.geoLocationService = geoLocationService;
        this.deviceInfoService = deviceInfoService;
    }
 
    public void recordLogin(HttpServletRequest request,
            Long orgcode,
            String usercd,
            Long prodcode,
            boolean success,
            String failureReason) {
        try {
            LoginHistoryDto dto = new LoginHistoryDto();
            dto.setOrgcode(orgcode);
            dto.setProdcode(prodcode);
            dto.setUsercd(usercd);
            dto.setLoginTime(LocalDateTime.now());
            dto.setLoginStatus(success ? 1 : 0);
            dto.setFailureReason(failureReason);
 
            String clientIp = normalizeIp(resolveClientIp(request));
            dto.setIpAddress(clientIp);
            dto.setGeoLocation(geoLocationService.resolveGeoLocation(clientIp));
 
            dto.setDeviceInfo(deviceInfoService.parseDeviceInfo(
                    request.getHeader("User-Agent"),
                    request.getHeader("Sec-CH-UA-Platform-Version"),
                    request.getHeader("Sec-CH-UA"),
                    request.getHeader("Sec-CH-UA-Mobile"),
                    request.getHeader("Sec-CH-UA-Platform")));
            String rawDeviceId = request.getHeader("X-Device-Id");
 
            dto.setDeviceId(rawDeviceId);
            dto.setSessionId(request.getSession(true).getId());
            loginHistoryRepository.save(dto);
        } catch (Exception e) {
            System.err.println("[LoginHistory] recordLogin failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    public void recordLogout(HttpServletRequest request) {
        try {
            String usercd = null;
            Long orgcode = null;
 
            String authHeader = request.getHeader("Authorization");
 
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSigningKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
 
                    Object userScdObj = claims.get("userscd");
                    if (userScdObj != null) {
                        usercd = userScdObj.toString();
                    }
 
                    Object org = claims.get("orgCode");
                    if (org != null)
                        orgcode = Long.parseLong(org.toString());
 
                } catch (Exception jwtEx) {
                    System.err.println("[LoginHistory] JWT parse failed: " + jwtEx.getMessage());
                }
            }
 
            if (usercd != null && orgcode != null) {
                loginHistoryRepository.updateLogoutTimeByUserAndOrg(usercd, orgcode);
                return;
            }
 
            if (request.getSession(false) != null) {
                String sessionId = request.getSession(false).getId();
                loginHistoryRepository.updateLogoutTimeBySessionId(sessionId);
            }
 
        } catch (Exception e) {
            System.err.println("[LoginHistory] recordLogout failed: " + e.getMessage());
        }
    }
 
    private String resolveClientIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
 
        if (isPrivateIp(remoteAddr)) {
            String xForwardedFor = request.getHeader("X-Forwarded-For");
 
            if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
                String[] ips = xForwardedFor.split(",");
                for (String ip : ips) {
                    ip = ip.trim();
                    if (isValidIp(ip) && !"unknown".equalsIgnoreCase(ip)) {
                        return ip;
                    }
                }
            }
 
            String forwarded = request.getHeader("Forwarded");
            if (forwarded != null && forwarded.contains("for=")) {
                try {
                    String[] parts = forwarded.split(";");
                    for (String part : parts) {
                        if (part.trim().startsWith("for=")) {
                            String ip = part.split("=")[1].replace("\"", "").trim();
                            if (isValidIp(ip))
                                return ip;
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
 
        return remoteAddr;
    }
 
    private boolean isPrivateIp(String ip) {
        return ip.startsWith("10.")
                || ip.startsWith("192.168.")
                || ip.startsWith("172.")
                || ip.equals("127.0.0.1")
                || ip.equals("0:0:0:0:0:0:0:1");
    }
 
    private boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty())
            return false;
 
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    private String normalizeIp(String ip) {
        if (ip == null || ip.trim().isEmpty())
            return "—";
 
        try {
            if (ip.startsWith("::ffff:")) {
                ip = ip.substring(7);
            }
 
            InetAddress inetAddress = InetAddress.getByName(ip);
            if (inetAddress.isLoopbackAddress()) {
                return "127.0.0.1";
            }
 
            return inetAddress.getHostAddress();
 
        } catch (Exception e) {
            return ip;
        }
    }
 
    public List<LoginHistoryDto> getAllRecords() {
        List<LoginHistoryDto> list = loginHistoryRepository.findAllOrderByLoginTimeDesc();
        list.forEach(dto -> dto.setProdname(resolveProductName(dto.getProdcode())));
        return list;
    }
 
    public List<LoginHistoryDto> getByOrgcode(Long orgcode) {
        List<LoginHistoryDto> list = loginHistoryRepository.findByOrgcodeOrderByLoginTimeDesc(orgcode);
        list.forEach(dto -> dto.setProdname(resolveProductName(dto.getProdcode())));
        return list;
    }
 
    public List<LoginHistoryDto> getByOrgcodeAndProduct(Long orgcode, Long prodcode) {
        List<LoginHistoryDto> list = loginHistoryRepository
                .findByOrgcodeAndProdcodeOrderByLoginTimeDesc(orgcode, prodcode);
        list.forEach(dto -> dto.setProdname(resolveProductName(dto.getProdcode())));
        return list;
    }
 
    public List<LoginHistoryDto> getByOrgcodeAndUser(Long orgcode, String usercd) {
        List<LoginHistoryDto> list = loginHistoryRepository
                .findByOrgcodeAndUsercdOrderByLoginTimeDesc(orgcode, usercd);
        list.forEach(dto -> dto.setProdname(resolveProductName(dto.getProdcode())));
        return list;
    }
 
    public List<LoginHistoryDto> getByOrgcodeUserAndProduct(Long orgcode, String usercd, Long prodcode) {
        List<LoginHistoryDto> list = loginHistoryRepository
                .findByOrgcodeAndUsercdAndProdcodeOrderByLoginTimeDesc(orgcode, usercd, prodcode);
        list.forEach(dto -> dto.setProdname(resolveProductName(dto.getProdcode())));
        return list;
    }
 
    public String getLastAccessedApp(Long orgcode, String usercd) {
        LoginHistoryDto last = loginHistoryRepository.findLastSuccessfulLogin(orgcode, usercd);
        if (last == null)
            return "None";
        return resolveProductName(last.getProdcode());
    }
 
    private String resolveProductName(Long prodcode) {
        if (prodcode == null)
            return "Unknown";
        return productRepository.findByProdCode(prodcode)
                .map(p -> p.getProdname())
                .orElse("Product-" + prodcode);
    }
 
    public void recordLoginInternal(LoginHistoryDto dto) {
        try {
            dto.setLoginTime(LocalDateTime.now());
            String normalizedIp = normalizeIp(dto.getIpAddress());
            dto.setIpAddress(normalizedIp);
            dto.setGeoLocation(geoLocationService.resolveGeoLocation(normalizedIp));
            loginHistoryRepository.save(dto);
        } catch (Exception e) {
        }
    }
 
    public void recordLogoutInternal(String usercd, Long orgcode) {
        loginHistoryRepository.updateLogoutTimeByUserAndOrg(usercd, orgcode);
    }
 
    public void recordExchangeLogin(HttpServletRequest httpRequest,
            Long orgcode,
            String usercd,
            String userName,
            Long prodcode) {
        try {
            LoginHistoryDto dto = new LoginHistoryDto();
            dto.setOrgcode(orgcode);
            dto.setUsercd(usercd);
            dto.setUserName(userName);
            dto.setProdcode(prodcode);
            dto.setLoginStatus(1);
            dto.setIpAddress(resolveClientIp(httpRequest));
 
            dto.setDeviceInfo(deviceInfoService.parseDeviceInfo(
                    httpRequest.getHeader("User-Agent"),
                    httpRequest.getHeader("Sec-CH-UA-Platform-Version"),
                    httpRequest.getHeader("Sec-CH-UA"),
                    httpRequest.getHeader("Sec-CH-UA-Mobile"),
                    httpRequest.getHeader("Sec-CH-UA-Platform")));
 
            dto.setDeviceId(httpRequest.getHeader("X-Device-Id"));
            dto.setSessionId(httpRequest.getSession(true).getId());
            recordLoginInternal(dto);
 
        } catch (Exception e) {
            System.err.println("[LoginHistory] recordExchangeLogin failed: " + e.getMessage());
        }
    }
}
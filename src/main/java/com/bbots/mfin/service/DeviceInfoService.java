package com.bbots.mfin.service;
 
import org.springframework.stereotype.Service;
 
@Service
public class DeviceInfoService {
 
    public String parseDeviceInfo(String userAgent,
            String platformVersion,
            String secChUa,
            String secChUaMobile,
            String secChUaPlatform) {
 
        if (userAgent == null && secChUa == null)
            return "Unknown";
 
        String browser = detectBrowserFromHints(secChUa, userAgent);
        String os = detectOSFromHints(secChUaPlatform, userAgent);
        String device = detectDeviceFromHints(secChUaMobile, userAgent);
 
        return browser + " on " + os + " (" + device + ")";
    }
 
    private String detectBrowserFromHints(String secChUa, String ua) {
        if (secChUa != null && !secChUa.isEmpty()) {
            String hint = secChUa.toLowerCase();
            if (hint.contains("microsoft edge"))
                return "Microsoft Edge";
            if (hint.contains("opera"))
                return "Opera";
            if (hint.contains("samsung browser") || hint.contains("samsungbrowser"))
                return "Samsung Browser";
            if (hint.contains("firefox"))
                return "Firefox";
            if (hint.contains("google chrome"))
                return "Google Chrome";
            if (hint.contains("chromium"))
                return "Chromium";
        }
        return detectBrowserFromUA(ua);
    }
 
    private String detectBrowserFromUA(String ua) {
        if (ua == null)
            return "Unknown Browser";
        if (ua.contains("Edg/"))
            return "Microsoft Edge";
        if (ua.contains("OPR/") || ua.contains("Opera/"))
            return "Opera";
        if (ua.contains("SamsungBrowser/"))
            return "Samsung Browser";
        if (ua.contains("Firefox/") && !ua.contains("Seamonkey/"))
            return "Firefox";
        if (ua.contains("Chrome/") && !ua.contains("Chromium/"))
            return "Google Chrome";
        if (ua.contains("Chromium/"))
            return "Chromium";
        if (ua.contains("Safari/") && ua.contains("Version/"))
            return "Safari";
        if (ua.contains("MSIE") || ua.contains("Trident/"))
            return "Internet Explorer";
        return "Unknown Browser";
    }
 
    private String detectOSFromHints(String secChUaPlatform, String ua) {
        if (secChUaPlatform != null && !secChUaPlatform.isEmpty()) {
            String platform = secChUaPlatform.replace("\"", "").trim();
            if (!platform.isEmpty() && !platform.equalsIgnoreCase("Unknown")) {
                return platform;
            }
        }
        return detectOSFromUA(ua);
    }
 
    private String detectOSFromUA(String ua) {
        if (ua == null)
            return "Unknown OS";
        if (ua.contains("Windows NT"))
            return "Windows";
        if (ua.contains("iPhone"))
            return "iOS";
        if (ua.contains("iPad"))
            return "iPadOS";
        if (ua.contains("Android"))
            return "Android";
        if (ua.contains("Mac OS X"))
            return "macOS";
        if (ua.contains("Linux"))
            return "Linux";
        return "Unknown OS";
    }
 
    private String detectDeviceFromHints(String secChUaMobile, String ua) {
        if (secChUaMobile != null && !secChUaMobile.isEmpty()) {
            if (secChUaMobile.trim().equals("?1"))
                return "Mobile";
            if (secChUaMobile.trim().equals("?0")) {
                if (ua != null && (ua.contains("iPad") ||
                        (ua.contains("Android") && !ua.contains("Mobile")))) {
                    return "Tablet";
                }
                return "Desktop";
            }
        }
        return detectDeviceFromUA(ua);
    }
 
    private String detectDeviceFromUA(String ua) {
        if (ua == null)
            return "Unknown";
        if (ua.contains("iPhone"))
            return "Mobile";
        if (ua.contains("Android") && ua.contains("Mobile"))
            return "Mobile";
        if (ua.contains("iPad"))
            return "Tablet";
        if (ua.contains("Android") && !ua.contains("Mobile"))
            return "Tablet";
        return "Desktop";
    }
}
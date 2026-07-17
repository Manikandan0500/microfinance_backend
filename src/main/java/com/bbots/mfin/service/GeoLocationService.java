package com.bbots.mfin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.InetAddress;

@Service
public class GeoLocationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String resolveGeoLocation(String ipAddress) {
        if (ipAddress == null || ipAddress.trim().isEmpty()) return "—";

        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            if (inetAddress.isLoopbackAddress())  return "Localhost";
            if (inetAddress.isSiteLocalAddress()) return "Local Network";
            if (inetAddress.isAnyLocalAddress())  return "Local Network";
            if (inetAddress.isLinkLocalAddress()) return "Local Network";
        } catch (Exception e) {
            return "—";
        }

        try {
            String url = "https://ipwho.is/" + ipAddress;
            String response = restTemplate.getForObject(url, String.class);
            JsonNode node = objectMapper.readTree(response);

            if (!node.path("success").asBoolean()) return "—";

            String city    = node.path("city").asText("—");
            String region  = node.path("region").asText("—");
            String country = node.path("country").asText("—");

            return city + ", " + region + ", " + country;

        } catch (Exception e) {
            return "—";
        }
    }
}
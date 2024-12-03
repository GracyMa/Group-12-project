package com.gracyma.onlineshoppingproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;

@Service
public class EbayAuthService {

    private String clientId;
    private String clientSecret;
    private RestTemplate restTemplate;

    private static final String TOKEN_URL = "https://api.ebay.com/identity/v1/oauth2/token";

    @Autowired
    public EbayAuthService(RestTemplate restTemplate) {
        Dotenv dotenv = Dotenv.load();
        this.clientId = dotenv.get("EBAY_CLIENT_ID");
        this.clientSecret = dotenv.get("EBAY_CLIENT_SECRET");
        this.restTemplate = restTemplate;
    }

    public String fetchAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials&scope=https://api.ebay.com/oauth/api_scope";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                TOKEN_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("access_token")) {
            return responseBody.get("access_token").toString();
        } else {
            throw new RuntimeException("Failed to fetch access token from eBay.");
        }
    }
}

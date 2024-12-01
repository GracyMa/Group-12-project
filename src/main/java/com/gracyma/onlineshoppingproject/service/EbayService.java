package com.gracyma.onlineshoppingproject.service;

import com.gracyma.onlineshoppingproject.model.ItemSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EbayService {

    @Value("${ebay.api.base-url}")
    private String baseUrl;

    @Value("${ebay.api.token}")
    private String apiToken;

    private final RestTemplate restTemplate;

    public EbayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ItemSummary> searchItems(String keyword, int limit) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("q", keyword)
                .queryParam("limit", limit)
                .toUriString();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.set("Content-Type", "application/json");

        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        Map<String, Object> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                entity,
                Map.class
        ).getBody();

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("itemSummaries");
        List<ItemSummary> result = new ArrayList<>();

        if (items != null) {
            for (Map<String, Object> item : items) {
                String title = (String) item.get("title");
                Map<String, Object> price = (Map<String, Object>) item.get("price");
                String priceValue = price != null ? (String) price.get("value") : "N/A";
                Map<String, Object> image = (Map<String, Object>) item.get("image");
                String imageUrl = image != null ? (String) image.get("imageUrl") : "N/A";

                result.add(new ItemSummary(title, priceValue, imageUrl));
            }
        }
        return result;
    }
}

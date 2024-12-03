package com.gracyma.onlineshoppingproject.service;

import com.gracyma.onlineshoppingproject.model.ItemSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EbayService {

    @Autowired
    private EbayAuthService ebayAuthService;

    @Autowired
    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://api.ebay.com/buy/browse/v1/item_summary/search";

    public EbayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ItemSummary> searchItems(String keyword, int limit) {
        String accessToken = ebayAuthService.fetchAccessToken();

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", keyword)
                .queryParam("limit", limit)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
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

package com.gracyma.onlineshoppingproject.controller;

import com.gracyma.onlineshoppingproject.model.ItemSummary;
import com.gracyma.onlineshoppingproject.service.EbayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EbayController {

    private final EbayService ebayService;

    public EbayController(EbayService ebayService) {
        this.ebayService = ebayService;
    }

    @GetMapping("/search")
    public List<ItemSummary> searchItems(@RequestParam String keyword, @RequestParam int limit) {
        return ebayService.searchItems(keyword, limit);
    }
}
package com.gracyma.onlineshoppingproject.controller;

import com.gracyma.onlineshoppingproject.model.ItemSummary;
import com.gracyma.onlineshoppingproject.service.EbayService;
import com.gracyma.onlineshoppingproject.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class ProductController {
    @Resource
    private SearchService searchService;

    @Resource
    private EbayService ebayService;

    @GetMapping("/")
    public String loadInitialPage(Model model) {
        model.addAttribute("itemList", Collections.emptyList());
        return "list_items";
    }

    @PostMapping("/upload")
    public String uploadAndSearch(@RequestParam("image") MultipartFile imageFile, Model model) {
        try {
            String filePath = System.getProperty("java.io.tmpdir") + "/" + imageFile.getOriginalFilename();
            File file = new File(filePath);
            imageFile.transferTo(file);
            System.out.println("File saved to: " + filePath);

            List<String> labels = searchService.imageToLabels(filePath, 5);
            System.out.println("Labels detected: " + labels);

            if (labels == null || labels.isEmpty()) {
                model.addAttribute("error", "No labels detected from the uploaded image.");
                return "list_items";
            }

            String keyword = labels.get(0);
            List<ItemSummary> items = ebayService.searchItems(keyword, 10);

            model.addAttribute("itemList", items);
            return "list_items";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to process the image: " + e.getMessage());
            return "list_items";
        }
    }
}

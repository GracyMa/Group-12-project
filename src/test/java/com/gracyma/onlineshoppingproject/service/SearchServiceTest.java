package com.gracyma.onlineshoppingproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = "spring.config.location=classpath:/application.properties")
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Test
    void testImageToLabels() {
        String imagePath = "src/test/resources/sample.jpeg";
        File file = new File(imagePath);
        int maxLabels = 5;

        try {
            List<String> labels = searchService.imageToLabels(file.getAbsolutePath(), maxLabels);
            System.out.println("Detected Labels:");
            labels.forEach(System.out::println);

            assertNotNull(labels);
        } catch (Exception e) {
            System.err.println("Error during Rekognition API call: " + e.getMessage());
        }
    }
}
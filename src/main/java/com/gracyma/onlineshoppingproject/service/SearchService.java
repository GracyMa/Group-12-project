package com.gracyma.onlineshoppingproject.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.gracyma.onlineshoppingproject.common.AmzRekoClientProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

@Service
public class SearchService {

    @Resource
    private AmzRekoClientProvider amzRekoClientProvider = new AmzRekoClientProvider();

    public List<String> imageToLabels(String imagePath, int maxLabels){

        AmazonRekognition rekognitionClient = amzRekoClientProvider.getRekognitionClient();;
        try {
            ByteBuffer imageBytes = ByteBuffer.wrap(Files.readAllBytes(Paths.get(imagePath)));
            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withBytes(imageBytes))
                    .withMaxLabels(10)
                    .withMinConfidence(75F);
            DetectLabelsResult result = rekognitionClient.detectLabels(request);

            List<String> labels = new ArrayList<>();
            for (Label label : result.getLabels()) {
                labels.add(label.getName());
            }
            return labels;
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image: " + e.getMessage(), e);
        }
    }
}

package com.gracyma.onlineshoppingproject.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.gracyma.onlineshoppingproject.common.AmzRekoClientProvider;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SearchService {

    @Resource
    private AmzRekoClientProvider amzRekoClientProvider = new AmzRekoClientProvider();

    public List<String> imageToLabels(String imagePath, int maxLabels){

        AmazonRekognition rekognitionClient = amzRekoClientProvider.getRekognitionClient();;


        //String imagePath = "/home/taiwei/mp/Group-12-project/iphone.jpg";
        try {
            ByteBuffer imageBytes = ByteBuffer.wrap(Files.readAllBytes(Paths.get(imagePath)));
            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withBytes(imageBytes))
                    .withMaxLabels(10)
                    .withMinConfidence(75F);

            //DetectLabelsResult result = rekognitionClient.detectLabels(request);
            DetectLabelsResult result = rekognitionClient.detectLabels(request);


            StringBuilder response = new StringBuilder("Detected Labels:\n");
            for (Label label : result.getLabels()) {
//                response.append(label.getName())
//                        .append(": ")
//                        .append(label.getConfidence())
//                        .append("%\n");
                System.out.println(label.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

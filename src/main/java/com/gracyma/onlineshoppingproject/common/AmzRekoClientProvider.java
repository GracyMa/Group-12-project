package com.gracyma.onlineshoppingproject.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class AmzRekoClientProvider {
    @Value(value = "${aws.accessKey}")
    private String accessKey;

    @Value(value = "${aws.secretKey}")
    private String secretKey;


    @Value(value = "${aws.region}")
    private String region;



    public AmazonRekognition getRekognitionClient() {
        log.info(secretKey);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonRekognitionClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}

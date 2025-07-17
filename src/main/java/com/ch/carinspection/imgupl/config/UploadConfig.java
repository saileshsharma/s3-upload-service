package com.ch.carinspection.imgupl.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for AWS S3 client setup.
 * <p>
 * Creates and configures an {@link AmazonS3} bean with credentials and region
 * loaded from application properties.
 * </p>
 *
 * AWS credentials and region are injected from properties:
 * <ul>
 *   <li>aws.accessKeyId</li>
 *   <li>aws.secretAccessKey</li>
 *   <li>aws.region</li>
 * </ul>
 *
 * @author YourName
 * @version 1.0
 */
@Configuration
public class UploadConfig {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    /**
     * Creates an {@link AmazonS3} client bean configured with static AWS credentials and region.
     *
     * @return configured {@link AmazonS3} client
     */
    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials creds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
    }
}

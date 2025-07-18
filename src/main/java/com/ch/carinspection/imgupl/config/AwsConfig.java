package com.ch.carinspection.imgupl.config;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AwsConfig {

    private final Dotenv dotenv;

    public AwsConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Bean
    public AmazonS3 amazonS3() {
        String region = dotenv.get("AWS_REGION");
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .build();
    }
}
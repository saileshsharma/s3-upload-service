package com.ch.carinspection.imgupl.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.ch.carinspection.imgupl.dto.UploadResponseDTO;
import com.ch.carinspection.imgupl.model.Image;
import com.ch.carinspection.imgupl.model.ScanStatus;
import com.ch.carinspection.imgupl.service.ProgressTracker;
import com.ch.carinspection.imgupl.service.RabbitMQSender;
import com.ch.carinspection.imgupl.service.UploadService;
import com.ch.carinspection.imgupl.service.repository.ImageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
@Tag(name = "Upload Service", description = "Handles image uploads to AWS S3")
public class UploadServiceImpl implements UploadService {

    private final AmazonS3 s3Client;
    private final ImageRepository imageRepository;
    private final RabbitMQSender rabbitMQSender;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public UploadServiceImpl(AmazonS3 s3Client,
                             ImageRepository imageRepository,
                             RabbitMQSender rabbitMQSender) {
        this.s3Client = s3Client;
        this.imageRepository = imageRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Override
    @Operation(
            summary = "Upload image to AWS S3",
            description = "Uploads an image to S3, stores metadata to DB, and sends scan message to RabbitMQ"
    )
    public UploadResponseDTO upload(MultipartFile file) throws IOException, InterruptedException {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isBlank()) {
            filename = "default-" + System.currentTimeMillis();
        }

        long startTime = System.currentTimeMillis();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        PutObjectRequest request = new PutObjectRequest(bucketName, filename, file.getInputStream(), metadata);

        // Track upload progress
        ProgressTracker tracker = new ProgressTracker();
        request.setGeneralProgressListener(tracker);

        TransferManager tm = TransferManagerBuilder.standard()
                .withS3Client(s3Client)
                .withMultipartUploadThreshold(5 * 1024 * 1024L) // 5 MB threshold
                .build();

        Upload upload = tm.upload(request);
        upload.waitForCompletion();
        tm.shutdownNow();

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        int partCount = tracker.getPartCount();
        double iops = duration > 0 ? partCount / duration : 0;

        String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, filename);

        // Create and save image metadata
        Image image = new Image();
        image.setImageName(filename);
        image.setImageUrl(fileUrl);
        image.setSizeMb(Math.round((file.getSize() / (1024.0 * 1024.0)) * 100.0) / 100.0);
        image.setTimestamp(Instant.now());
        image.setContentType(file.getContentType());
        image.setApproxNetworkCalls(partCount);
        image.setApproxIops(Math.round(iops * 100.0) / 100.0);
        image.setScanStatus(ScanStatus.PENDING);

        Image saved = imageRepository.saveAndFlush(image);

        // Send message to RabbitMQ for further scanning
        rabbitMQSender.sendImageScanMessage(saved.getId());

        return UploadResponseDTO.builder()
                .id(saved.getId())
                .filename(filename)
                .sizeBytes(file.getSize())
                .uploadTimeSec(duration)
                .url(fileUrl)
                .timestamp(saved.getTimestamp())
                .contentType(saved.getContentType())
                .approxNetworkCalls(partCount)
                .approxIops(saved.getApproxIops())
                .scanStatus(saved.getScanStatus())
                .uploadMethod("Backend")
                .build();
    }
}

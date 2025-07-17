package com.ch.carinspection.imgupl.rabbit;

import com.ch.carinspection.imgupl.service.repository.ImageRepository;
import com.ch.carinspection.imgupl.model.ScanStatus;
import com.ch.carinspection.imgupl.model.Image;
import com.ch.carinspection.imgupl.service.ScanService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * RabbitMQ consumer component that listens to the "imageScanQueue" queue
 * for image scan requests. Upon receiving an image ID, it fetches the image
 * metadata from the database, performs a virus scan using {@link ScanService},
 * updates the image's scan status, and persists the result back to the database.
 */
@Component
public class ImageScanConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ImageScanConsumer.class);

    private final ImageRepository imageRepository;
    private final ScanService scanService;

    /**
     * Constructs an {@code ImageScanConsumer} with the specified repository and scan service.
     *
     * @param imageRepository the repository to fetch and save {@link Image} entities
     * @param scanService the service to perform virus scanning on images
     */
    public ImageScanConsumer(ImageRepository imageRepository,
                             ScanService scanService) {
        this.imageRepository = imageRepository;
        this.scanService = scanService;
    }

    /**
     * Listens for image scan requests from RabbitMQ queue "imageScanQueue".
     * Parses the image ID, retrieves the image from the database, runs the virus scan,
     * updates the image scan status, and saves the updated image.
     *
     * @param imageIdStr the ID of the image to scan, received as a String message from the queue
     */
    @RabbitListener(queues = "imageScanQueue")
    public void processImageScan(String imageIdStr) {
        Long imageId = Long.parseLong(imageIdStr);

        Optional<Image> imageOpt = imageRepository.findById(imageId);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();

            // Perform virus scan on the image URL
            String result = scanService.scanImageFromS3(image.getImageUrl());

            // Update scan status and save to database
            image.setScanStatus(ScanStatus.valueOf(result));
            imageRepository.save(image);

            logger.info("✅ Scanned image ID {}: {}", imageId, result);
        } else {
            logger.error("❌ Image ID not found: {}", imageId);
        }
    }
}

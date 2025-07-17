package com.ch.carinspection.imgupl.listener;

import com.ch.carinspection.imgupl.service.repository.ImageRepository;
import com.ch.carinspection.imgupl.model.ScanStatus;
import com.ch.carinspection.imgupl.model.Image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Listener component for consuming image scan messages from the RabbitMQ queue "imageScanQueue".
 * Upon receiving a message containing an image ID, this listener fetches the corresponding Image entity,
 * simulates scanning the image, updates the scan status, and persists the changes.
 */
@Component
public class ScanListener {

    private static final Logger logger = LoggerFactory.getLogger(ScanListener.class);

    private final ImageRepository imageRepository;

    /**
     * Constructs a new {@code ScanListener} with the given {@link ImageRepository}.
     *
     * @param imageRepository the repository used to access Image entities from the database
     */
    public ScanListener(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Receives and processes messages from the RabbitMQ queue "imageScanQueue".
     * The message is expected to be a String representing the image ID.
     * <p>
     * The method parses the ID, fetches the image from the repository, simulates scanning
     * by marking the image as CLEAN, and saves the updated image back to the database.
     * Logs output to the console for success and error cases.
     *
     * @param imageIdStr the image ID received as a String message from the queue
     */
    @RabbitListener(queues = "imageScanQueue")
    public void receiveMessage(String imageIdStr) {
        try {
            Long imageId = Long.parseLong(imageIdStr);
            Optional<Image> optionalImage = imageRepository.findById(imageId);

            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();

                // Simulate scanning logic (replace with real scanner)
                logger.info("Scanning image ID: {}", imageId);

                // Example: mark as CLEAN after scan
                image.setScanStatus(ScanStatus.CLEAN);

                imageRepository.save(image);

                logger.info("Scan completed for image ID: {}", imageId);
            } else {
                logger.error("Image not found for ID: {}", imageId);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid image ID received: {}", imageIdStr, e);
        }
    }
}

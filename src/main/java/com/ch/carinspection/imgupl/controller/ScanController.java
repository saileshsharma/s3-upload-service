package com.ch.carinspection.imgupl.controller;

import com.ch.carinspection.imgupl.service.repository.ImageRepository;
import com.ch.carinspection.imgupl.service.RabbitMQSender;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing image scan operations.
 * <p>
 * Provides endpoints to trigger malware scan messages for images
 * and to retrieve image metadata from the database.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
@RestController
@RequestMapping("/api/scan")
@Tag(name = "Scan", description = "APIs for managing image scan operations")
public class ScanController {

    private final RabbitMQSender rabbitMQSender;
    private final ImageRepository imageRepository;

    /**
     * Constructs the ScanController with required dependencies.
     *
     * @param rabbitMQSender service to send scan trigger messages
     * @param imageRepository repository to access image metadata
     */
    public ScanController(RabbitMQSender rabbitMQSender,
                          ImageRepository imageRepository) {
        this.rabbitMQSender = rabbitMQSender;
        this.imageRepository = imageRepository;
    }

    /**
     * POST endpoint to trigger a scan for the image with the given ID.
     *
     * @param id the ID of the image to scan
     * @return HTTP 200 with confirmation message if image found and scan triggered,
     *         or HTTP 404 if image ID not found
     */
    @PostMapping("/{id}")
    public ResponseEntity<?> triggerScan(@PathVariable Long id) {
        return imageRepository.findById(id).map(image -> {
            rabbitMQSender.sendImageScanMessage(image.getId());
            return ResponseEntity.ok("✅ Scan triggered for image ID: " + id);
        }).orElse(ResponseEntity.status(404).body("❌ Image not found with ID: " + id));
    }

    /**
     * GET endpoint to retrieve details of the image with the given ID.
     *
     * @param id the ID of the image
     * @return HTTP 200 with image metadata if found, or HTTP 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getImageDetails(@PathVariable Long id) {
        return imageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

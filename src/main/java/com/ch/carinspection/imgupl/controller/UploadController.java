package com.ch.carinspection.imgupl.controller;

import com.ch.carinspection.imgupl.dto.UploadResponseDTO;
import com.ch.carinspection.imgupl.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * REST controller providing APIs to upload images to AWS S3.
 * <p>
 * This controller exposes an endpoint to upload image files. Uploaded images are
 * handled by {@link UploadService}, which uploads the file to AWS S3, stores metadata
 * in the database, and returns upload details as a response.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Image Upload", description = "APIs for uploading images to AWS S3")
public class UploadController {

    private final UploadService uploadService;

    /**
     * Constructs an UploadController with the required UploadService dependency.
     *
     * @param uploadService the service responsible for handling image uploads
     */
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * Handles the HTTP POST request to upload an image file.
     * <p>
     * Accepts a multipart file with parameter name "file". Delegates the upload
     * process to {@link UploadService} and returns metadata about the uploaded file.
     * </p>
     *
     * @param file the image file to upload
     * @return a ResponseEntity containing upload details on success or error message on failure
     */
    @Operation(summary = "Upload image file", description = "Uploads an image file to AWS S3 and returns metadata.")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            UploadResponseDTO result = uploadService.upload(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

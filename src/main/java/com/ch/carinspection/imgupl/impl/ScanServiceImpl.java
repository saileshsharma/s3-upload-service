package com.ch.carinspection.imgupl.impl;

import com.ch.carinspection.imgupl.service.ScanService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link ScanService} that provides simulated
 * behavior for uploading files to S3 and scanning images.
 * <p>
 * This class is annotated with {@link Service} to be detected
 * as a Spring-managed bean.
 */
@Service
public class ScanServiceImpl extends ScanService {

    /**
     * Simulates uploading a file to an S3 bucket by generating
     * a unique URL for the file based on a UUID and the original
     * filename.
     *
     * @param file the multipart file to be uploaded
     * @return a simulated S3 URL string representing the uploaded file location
     */
    @Override
    public String uploadToS3(MultipartFile file) {
        String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        return "https://your-bucket.s3.region.amazonaws.com/" + uniqueName;
    }

    /**
     * Simulates scanning the provided image file for malware.
     * This method currently returns a fixed status indicating
     * the image is clean.
     *
     * @param file the multipart file to be scanned
     * @return a string representing the scan result ("CLEAN" or "INFECTED")
     */
    @Override
    public String scanImage(MultipartFile file) {
        return "CLEAN"; // or "INFECTED"
    }
}

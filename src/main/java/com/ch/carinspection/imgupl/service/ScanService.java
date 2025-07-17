package com.ch.carinspection.imgupl.service;

import com.ch.carinspection.imgupl.model.ScanStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ScanService {

    // This method should be replaced with actual S3 upload logic
    public String uploadToS3(MultipartFile file) {
        // Placeholder URL – your actual S3 logic should return the uploaded image URL
        return "https://s3.amazonaws.com/bucket/" + file.getOriginalFilename();
    }

    /**
     * Scans the image from the S3 URL using clamscan
     */
    public String scanImageFromS3(String s3Url) {
        File tempFile = null;

        try {
            // Download image to temp file
            tempFile = File.createTempFile("clamav-scan-", UUID.randomUUID().toString() + ".tmp");
            try (InputStream in = new URL(s3Url).openStream()) {
                Files.copy(in, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            // Run clamscan command
            ProcessBuilder pb = new ProcessBuilder("clamscan", tempFile.getAbsolutePath());
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean infected = false;

            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Optional: print ClamAV output
                if (line.contains("FOUND")) {
                    infected = true;
                    break;
                }
            }

            return infected ? ScanStatus.INFECTED.name() : ScanStatus.CLEAN.name();

        } catch (Exception e) {
            e.printStackTrace();
            return ScanStatus.SCAN_FAILED.name();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * Optional: To allow override with multipart logic if needed in future
     */
    public String scanImage(MultipartFile file) {
        // Optional implementation if scanning from raw file instead of URL
        return ScanStatus.PENDING.name();
    }
}
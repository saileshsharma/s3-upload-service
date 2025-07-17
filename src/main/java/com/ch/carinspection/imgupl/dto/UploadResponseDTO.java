package com.ch.carinspection.imgupl.dto;

import com.ch.carinspection.imgupl.model.ScanStatus;
import java.time.Instant;

/**
 * Data Transfer Object (DTO) representing the response details after uploading an image.
 * <p>
 * This DTO is immutable and built using the {@link Builder} pattern.
 * It contains metadata about the uploaded image including its ID, file details,
 * upload performance metrics, and scan status.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class UploadResponseDTO {

    private final Long id;
    private final String filename;
    private final long sizeBytes;
    private final double uploadTimeSec;
    private final String url;
    private final Instant timestamp;
    private final String contentType;
    private final int approxNetworkCalls;
    private final double approxIops;
    private final ScanStatus scanStatus;
    private final String uploadMethod;

    /**
     * Private constructor to enforce the use of {@link Builder}.
     *
     * @param builder the builder containing the data to initialize this DTO
     */
    private UploadResponseDTO(Builder builder) {
        this.id = builder.id;
        this.filename = builder.filename;
        this.sizeBytes = builder.sizeBytes;
        this.uploadTimeSec = builder.uploadTimeSec;
        this.url = builder.url;
        this.timestamp = builder.timestamp;
        this.contentType = builder.contentType;
        this.approxNetworkCalls = builder.approxNetworkCalls;
        this.approxIops = builder.approxIops;
        this.scanStatus = builder.scanStatus;
        this.uploadMethod = builder.uploadMethod;
    }

    // Getters

    /** @return the unique ID of the uploaded image record */
    public Long getId() { return id; }

    /** @return the filename of the uploaded image */
    public String getFilename() { return filename; }

    /** @return the size of the uploaded file in bytes */
    public long getSizeBytes() { return sizeBytes; }

    /** @return the time taken to upload the file, in seconds */
    public double getUploadTimeSec() { return uploadTimeSec; }

    /** @return the publicly accessible URL of the uploaded image */
    public String getUrl() { return url; }

    /** @return the timestamp when the image was uploaded */
    public Instant getTimestamp() { return timestamp; }

    /** @return the MIME content type of the uploaded file */
    public String getContentType() { return contentType; }

    /** @return the approximate number of network calls made during upload */
    public int getApproxNetworkCalls() { return approxNetworkCalls; }

    /** @return the approximate IOPS calculated during upload */
    public double getApproxIops() { return approxIops; }

    /** @return the scan status of the uploaded image */
    public ScanStatus getScanStatus() { return scanStatus; }

    /** @return the method used for the upload (e.g., "SpringBoot") */
    public String getUploadMethod() { return uploadMethod; }

    /**
     * Builder class for {@link UploadResponseDTO}.
     * <p>
     * Use this builder to create an immutable {@code UploadResponseDTO} instance.
     * </p>
     */
    public static class Builder {
        private Long id;
        private String filename;
        private long sizeBytes;
        private double uploadTimeSec;
        private String url;
        private Instant timestamp;
        private String contentType;
        private int approxNetworkCalls;
        private double approxIops;
        private ScanStatus scanStatus;
        private String uploadMethod;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder filename(String filename) { this.filename = filename; return this; }
        public Builder sizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; return this; }
        public Builder uploadTimeSec(double uploadTimeSec) { this.uploadTimeSec = uploadTimeSec; return this; }
        public Builder url(String url) { this.url = url; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }
        public Builder contentType(String contentType) { this.contentType = contentType; return this; }
        public Builder approxNetworkCalls(int approxNetworkCalls) { this.approxNetworkCalls = approxNetworkCalls; return this; }
        public Builder approxIops(double approxIops) { this.approxIops = approxIops; return this; }
        public Builder scanStatus(ScanStatus scanStatus) { this.scanStatus = scanStatus; return this; }
        public Builder uploadMethod(String uploadMethod) { this.uploadMethod = uploadMethod; return this; }

        /**
         * Builds and returns the immutable {@link UploadResponseDTO} instance.
         *
         * @return the constructed {@code UploadResponseDTO}
         */
        public UploadResponseDTO build() {
            return new UploadResponseDTO(this);
        }
    }

    /**
     * Creates a new builder instance for {@link UploadResponseDTO}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }
}

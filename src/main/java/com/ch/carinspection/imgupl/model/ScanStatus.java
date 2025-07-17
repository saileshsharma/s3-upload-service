package com.ch.carinspection.imgupl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum representing the scan status of an image.
 */
public enum ScanStatus {

    /** Scan is pending and not yet performed. */
    @JsonProperty("PENDING")
    PENDING,

    /** Image has been scanned and is clean (no threats detected). */
    @JsonProperty("CLEAN")
    CLEAN,

    /** Image has been scanned and is infected (threats detected). */
    @JsonProperty("INFECTED")
    INFECTED,

    /** Scan failed due to an error or exception. */
    @JsonProperty("SCAN_FAILED")
    SCAN_FAILED
}

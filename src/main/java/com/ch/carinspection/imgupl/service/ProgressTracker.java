package com.ch.carinspection.imgupl.service;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;

import com.amazonaws.event.ProgressListener;

/**
 * Tracks the number of parts completed during an AWS S3 multipart upload.
 */
public class ProgressTracker implements ProgressListener {

    private int partCount = 0;



    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        if (progressEvent.getEventType() == ProgressEventType.TRANSFER_PART_COMPLETED_EVENT) {
            partCount++;
        }
    }

    /**
     * Returns the count of completed parts uploaded.
     * @return the number of completed parts
     */
    public int getPartCount() {
        return partCount;
    }
}

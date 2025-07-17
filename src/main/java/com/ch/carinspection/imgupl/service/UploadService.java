

package com.ch.carinspection.imgupl.service;

import com.ch.carinspection.imgupl.dto.UploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    UploadResponseDTO upload(MultipartFile file) throws IOException, InterruptedException;
}

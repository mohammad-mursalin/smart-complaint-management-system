package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public ImageResponse uploadImage(MultipartFile imageFile) throws IOException;

    void deleteImage(String deleteHash);
}

package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.ImageResponse;
import com.mursalin.SCMS.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";

    @Value("${imgur.client.id}")
    private static String CLIENT_ID;

    @Override
    public ImageResponse uploadImage(MultipartFile imageFile) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Client-ID " + CLIENT_ID);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(imageFile.getBytes()) {
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ImageResponse> response = restTemplate.postForEntity(IMGUR_UPLOAD_URL, requestEntity, ImageResponse.class);

        if(response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to upload image to Imgur");
        }
        return response.getBody();
    }

    public void deleteImage(String deleteHash) {
        String deleteUrl = "https://api/imgur.com/3/image/" + deleteHash;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client_ID" + CLIENT_ID);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(deleteUrl,HttpMethod.DELETE, requestEntity, Void.class);

        if(!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to delete the image from Imgur");
        }
    }
}

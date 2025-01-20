package com.mursalin.SCMS.dto;

import lombok.Data;

@Data
public class ImageResponse {

    private Data data;
    private boolean success;
    private int status;

    @lombok.Data
    public static class Data {
        private String deleteHash;
        private String link;
    }

    public String getDeleteHash() {
        return data.getDeleteHash();
    }

    public String getImageUrl() {
        return data.getLink();
    }
}

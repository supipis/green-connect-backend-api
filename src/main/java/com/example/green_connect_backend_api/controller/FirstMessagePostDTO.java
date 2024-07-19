package com.example.green_connect_backend_api.controller;

public class FirstMessagePostDTO {

    String content;

    Long listingId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }
}

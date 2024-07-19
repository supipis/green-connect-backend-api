package com.example.green_connect_backend_api.controller;

import java.util.List;

public class GetMassageThreadDTO {
    List<GetMassageDTO> messages;
    private Long threadId;

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    Long listingId;

    public List<GetMassageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<GetMassageDTO> messages) {
        this.messages = messages;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public Long getThreadId() {
        return threadId;
    }
}

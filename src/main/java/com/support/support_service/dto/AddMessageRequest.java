package com.support.support_service.dto;

import jakarta.validation.constraints.NotBlank;

public class AddMessageRequest {

    @NotBlank(message = "Message cannot be empty")
    private String message;

    private boolean fromUser;

    public String getMessage() {
        return message;
    }

    public boolean isFromUser() {
        return fromUser;
    }
}

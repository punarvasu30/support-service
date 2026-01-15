package com.support.support_service.dto;

import com.support.support_service.model.enums.TicketPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTicketRequest {

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private TicketPriority priority;

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public TicketPriority getPriority() {
        return priority;
    }
}

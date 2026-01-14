package com.support.support_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import com.support.support_service.dto.AddMessageRequest;
import com.support.support_service.dto.CreateTicketRequest;
import com.support.support_service.model.Ticket;
import com.support.support_service.model.TicketMessage;
import com.support.support_service.model.enums.TicketStatus;
import com.support.support_service.service.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/support")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/debug")
public String debugHeaders(
        @RequestHeader("X-User-Email") String email,
        @RequestHeader("X-User-Role") String role
) {
    return email + " | " + role;
}


    // 1️⃣ Create a ticket
    @PostMapping("/tickets")
    public Ticket createTicket(
            @RequestBody @Valid CreateTicketRequest request
    ) {
        return ticketService.createTicket(
                request.getUserId(),
                request.getSubject(),
                request.getDescription(),
                request.getPriority()
        );
    }

    // 2️⃣ Get all tickets for a user
    @GetMapping("/user/{userId}")
    public List<Ticket> getUserTickets(@PathVariable Long userId) {
        return ticketService.getTicketsByUser(userId);
    }

    // 3️⃣ Get ticket by ID
    @GetMapping("/{ticketId}")
    public Ticket getTicket(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    // 4️⃣ Update ticket status
    @PutMapping("/{ticketId}/status")
    public Ticket updateStatus(
            @PathVariable Long ticketId,
            @RequestParam TicketStatus status
    ) {
        return ticketService.updateTicketStatus(ticketId, status);
    }

    @PostMapping("/{ticketId}/messages")
    public TicketMessage addMessage(
            @PathVariable Long ticketId,
            @RequestBody @Valid AddMessageRequest request
    ) {
        return ticketService.addMessage(
                ticketId,
                request.getMessage(),
                request.isFromUser()
        );
    }

    // 6️⃣ Get ticket messages
    @GetMapping("/{ticketId}/messages")
    public List<TicketMessage> getMessages(@PathVariable Long ticketId) {
        return ticketService.getMessages(ticketId);
    }
}

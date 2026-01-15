package com.support.support_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestHeader(value = "X-User-Email", required = false) String email,
            @RequestHeader(value = "X-User-Role", required = false) String role
    ) {
        return "email=" + email + ", role=" + role;
    }

    // 1️⃣ Create a ticket
    @PostMapping("/tickets")
    public Ticket createTicket(
            @RequestHeader(value = "X-User-Email", required = false) String email,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody @Valid CreateTicketRequest request
    ) {
        return ticketService.createTicket(
                email,
                request.getSubject(),
                request.getDescription(),
                request.getPriority()
        );
    }

    // 2️⃣ Get all tickets for a user
    @GetMapping("/my-tickets")
    public List<Ticket> getUserTickets(@RequestHeader(value = "X-User-Email", required = false) String email) {
        return ticketService.getTicketsByUser(email);
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

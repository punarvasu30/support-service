package com.support.support_service.service;

import java.util.List;

import com.support.support_service.model.Ticket;
import com.support.support_service.model.TicketMessage;
import com.support.support_service.model.enums.TicketPriority;
import com.support.support_service.model.enums.TicketStatus;

public interface TicketService {

    Ticket createTicket(
            String email,
            String subject,
            String description,
            TicketPriority priority
    );

    List<Ticket> getTicketsByEmail(String email);

    Ticket getTicketById(Long ticketId, String email);

    Ticket updateTicketStatus(Long ticketId, TicketStatus status);

    TicketMessage addMessage(
            Long ticketId,
            String email,
            String message
    );

    List<TicketMessage> getMessages(Long ticketId, String email);
}

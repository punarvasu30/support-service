package com.support.support_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.support.support_service.model.Ticket;
import com.support.support_service.model.TicketMessage;
import com.support.support_service.model.enums.SenderType;
import com.support.support_service.model.enums.TicketPriority;
import com.support.support_service.model.enums.TicketStatus;
import com.support.support_service.repository.TicketMessageRepository;
import com.support.support_service.repository.TicketRepository;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMessageRepository ticketMessageRepository;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            TicketMessageRepository ticketMessageRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.ticketMessageRepository = ticketMessageRepository;
    }

    @Override
    public Ticket createTicket(
            Long userId,
            String subject,
            String description,
            TicketPriority priority
    ) {
        Ticket ticket = Ticket.builder()
                .userId(userId)
                .subject(subject)
                .description(description)
                .priority(priority)
                .status(TicketStatus.OPEN)
                .build();

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public Ticket updateTicketStatus(Long ticketId, TicketStatus status) {
        Ticket ticket = getTicketById(ticketId);
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    @Override
    public TicketMessage addMessage(
            Long ticketId,
            String message,
            boolean isFromUser
    ) {
        Ticket ticket = getTicketById(ticketId);

        TicketMessage ticketMessage = TicketMessage.builder()
                .ticket(ticket)
                .message(message)
                .senderType(isFromUser ? SenderType.USER : SenderType.AGENT)
                .build();

        return ticketMessageRepository.save(ticketMessage);
    }

    @Override
    public List<TicketMessage> getMessages(Long ticketId) {
        return ticketMessageRepository
                .findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}

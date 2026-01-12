package com.support.support_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.support.support_service.model.TicketMessage;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {

    // All messages of a ticket
    List<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}

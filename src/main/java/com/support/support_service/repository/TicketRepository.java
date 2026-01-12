package com.support.support_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.support.support_service.model.Ticket;
import com.support.support_service.model.enums.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // All tickets of a user
    List<Ticket> findByUserId(Long userId);

    // Filter by status
    List<Ticket> findByStatus(TicketStatus status);

    // User tickets by status
    List<Ticket> findByUserIdAndStatus(Long userId, TicketStatus status);
}

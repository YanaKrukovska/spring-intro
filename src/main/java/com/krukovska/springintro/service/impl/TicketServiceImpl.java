package com.krukovska.springintro.service.impl;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import com.krukovska.springintro.repository.TicketRepository;
import com.krukovska.springintro.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketRepository.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketRepository.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketRepository.getBookedTickets(event, pageNum, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketRepository.cancelTicket(ticketId);
    }
}

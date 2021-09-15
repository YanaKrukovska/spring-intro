package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return null;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return null;
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return null;
    }

    public boolean cancelTicket(long ticketId) {
        return false;
    }
}

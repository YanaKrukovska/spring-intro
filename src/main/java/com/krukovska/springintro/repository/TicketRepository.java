package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import com.krukovska.springintro.model.dto.TicketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TicketRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {

        if (isPlaceBooked(eventId, place)) {
            return null;
        }

        long ticketId = getNextTicketId();
        var ticket = new TicketDto(ticketId, eventId, userId, category, place);
        storage.getTicketMap().put(ticketId, ticket);
        return ticket;
    }

    private boolean isPlaceBooked(long eventId, int place) {
        return storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getEventId() == eventId)
                .anyMatch(ticket -> ticket.getPlace() == place);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public boolean cancelTicket(long ticketId) {
        Map<Long, Ticket> ticketMap = storage.getTicketMap();
        var ticket = ticketMap.get(ticketId);
        if (ticket == null) {
            return false;
        }

        ticketMap.remove(ticketId);
        return true;
    }

    private long getNextTicketId() {
        return storage.getTicketMap().values()
                .stream()
                .mapToLong(Ticket::getId)
                .max()
                .orElse(0L) + 1;
    }
}

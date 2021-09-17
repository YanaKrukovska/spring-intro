package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import com.krukovska.springintro.model.dto.TicketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
        List<Ticket> tickets = storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .collect(Collectors.toList());

        return getPage(tickets, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> tickets = storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getEventId() == event.getId())
                .collect(Collectors.toList());

        return getPage(tickets, pageSize, pageNum);
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

    private List<Ticket> getPage(List<Ticket> entities, int pageSize, int pageNum) {
        int start = (pageNum - 1) * pageSize;
        if (entities == null || entities.size() <= start) {
            return Collections.emptyList();
        }

        return entities.subList(start, Math.min(start + pageSize, entities.size()));
    }

    private long getNextTicketId() {
        return storage.getTicketMap().values()
                .stream()
                .mapToLong(Ticket::getId)
                .max()
                .orElse(0L) + 1;
    }
}

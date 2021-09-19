package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import com.krukovska.springintro.model.dto.TicketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TicketRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {

        if (isPlaceBooked(eventId, place)) {
            log.debug("Can't book place {} for event {}. It's already booked", place, eventId);
            return null;
        }

        long ticketId = getNextTicketId();
        var ticket = new TicketDto(ticketId, eventId, userId, category, place);
        storage.getTicketMap().put(ticketId, ticket);
        log.debug("Booked category {} place {} for eventId {} for userId {}", category, place, eventId, userId);
        return ticket;
    }

    private boolean isPlaceBooked(long eventId, int place) {
        log.debug("Checking if place {} is booked for eventId {}", place, eventId);
        return storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getEventId() == eventId)
                .anyMatch(ticket -> ticket.getPlace() == place);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.debug("Getting booked tickets for userId {}, pageSize {}, pageNum {}", user.getId(), pageSize, pageNum);
        return storage.getTicketMap().values()
                .stream()
                .filter(ticket -> ticket.getUserId() == user.getId())
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.debug("Getting booked tickets for eventId {}, pageSize {}, pageNum {}", event.getId(), pageSize, pageNum);
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
            log.debug("Couldn't find ticketId {}. Failed to cancel", ticketId);
            return false;
        }

        ticketMap.remove(ticketId);
        log.debug("Canceled ticketId {}", ticketId);
        return true;
    }

    private long getNextTicketId() {
        log.debug("Searching next available id");
        return storage.getTicketMap().values()
                .stream()
                .mapToLong(Ticket::getId)
                .max()
                .orElse(0L) + 1;
    }
}

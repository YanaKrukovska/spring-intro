package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class Storage {

    public Map<Long, Event> eventMap = new HashMap<>();
    public Map<Long, Ticket> ticketMap = new HashMap<>();
    public Map<Long, User> userMap = new HashMap<>();


    public Map<Long, Event> getEventMap() {
        return eventMap;
    }

    public Map<Long, Ticket> getTicketMap() {
        return ticketMap;
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }
}
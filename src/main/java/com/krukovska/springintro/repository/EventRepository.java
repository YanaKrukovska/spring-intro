package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class EventRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Event getEventById(long eventId) {
        return storage.getEventMap().get(eventId);
    }

    public Event getEventByTitle(String title) {
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    public Event createEvent(Event event) {
        return null;
    }

    public Event updateEvent(Event event) {
        return null;
    }

    public boolean deleteEvent(long eventId) {
        return false;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }
}

package com.krukovska.springintro.service.impl;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.repository.EventRepository;
import com.krukovska.springintro.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event getEventById(long id) {
        return eventRepository.getEventById(id);
    }

    @Override
    public Event getEventByTitle(String title) {
        return eventRepository.getEventByTitle(title);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventRepository.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventRepository.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventRepository.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventRepository.deleteEvent(eventId);
    }
}

package com.krukovska.springintro.service;

import com.krukovska.springintro.model.Event;

import java.util.Date;
import java.util.List;

public interface EventService {

    Event getEventById(long id);

    Event getEventByTitle(String title);

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);

    Event createEvent(Event event);

    Event updateEvent(Event event);

    boolean deleteEvent(long eventId);
}

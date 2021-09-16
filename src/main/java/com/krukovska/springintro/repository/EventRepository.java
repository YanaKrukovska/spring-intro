package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return storage.getEventMap().values()
                .stream()
                .filter(entry -> entry.getTitle().contains(title))
                .findFirst()
                .orElse(null);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> allEvent = storage.getEventMap().values()
                .stream()
                .filter(entry -> entry.getTitle().contains(title))
                .collect(Collectors.toList());

        return getPage(allEvent, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        Map<Long, Event> eventMap = storage.getEventMap();
        long eventId = event.getId();
        if (eventMap.containsKey(eventId)) {
            return null;
        }
        eventMap.put(eventId, event);
        return event;
    }

    public Event updateEvent(Event event) {
        Map<Long, Event> eventMap = storage.getEventMap();
        long eventId = event.getId();
        if (!eventMap.containsKey(eventId)) {
            return null;
        }
        eventMap.put(eventId, event);
        return event;
    }

    public boolean deleteEvent(long eventId) {
        Map<Long, Event> eventMap = storage.getEventMap();

        if (eventMap.containsKey(eventId)) {
            eventMap.remove(eventId);
            return true;
        }
        return false;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> allEvent = storage.getEventMap().values()
                .stream()
                .filter(entry -> entry.getDate().equals(day))
                .collect(Collectors.toList());

        return getPage(allEvent, pageSize, pageNum);
    }

    private List<Event> getPage(List<Event> entities, int pageSize, int pageNum) {
        int start = (pageNum - 1) * pageSize;
        if (entities == null || entities.size() <= start) {
            return Collections.emptyList();
        }

        return entities.subList(start, Math.min(start + pageSize, entities.size()));
    }
}

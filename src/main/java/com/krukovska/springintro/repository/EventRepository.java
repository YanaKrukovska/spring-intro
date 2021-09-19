package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class EventRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Event getEventById(long eventId) {
        log.debug("Getting event with id {}", eventId);
        return storage.getEventMap().get(eventId);
    }

    public Event getEventByTitle(String title) {
        log.debug("Getting event by title " + title);
        return storage.getEventMap().values()
                .stream()
                .filter(entry -> entry.getTitle().contains(title))
                .findFirst()
                .orElse(null);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.debug("Getting events with title {}, pageSize {}, pageNum {}", title, pageSize, pageNum);
        return storage.getEventMap().values()
                .stream()
                .filter(entry -> entry.getTitle().contains(title))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Event createEvent(Event event) {
        log.debug("Creating new event {}", event);
        Map<Long, Event> eventMap = storage.getEventMap();
        long eventId = event.getId();
        if (eventMap.containsKey(eventId)) {
            log.debug("Can't create new event. Id {} already used", eventId);
            return null;
        }
        eventMap.put(eventId, event);
        return event;
    }

    public Event updateEvent(Event event) {
        log.debug("Updating event with id {}. Updated entity {}", event.getId(), event);
        Map<Long, Event> eventMap = storage.getEventMap();
        long eventId = event.getId();
        if (!eventMap.containsKey(eventId)) {
            log.debug("Can't update event. Couldn't find entity with id {}", eventId);
            return null;
        }
        eventMap.put(eventId, event);
        log.debug("Updated event with id {}, updated event {}", eventId, event);
        return event;
    }

    public boolean deleteEvent(long eventId) {
        Map<Long, Event> eventMap = storage.getEventMap();

        if (eventMap.containsKey(eventId)) {
            eventMap.remove(eventId);
            log.debug("Deleted event with id {}", eventId);
            return true;
        }

        log.debug("Couldn't find entity to delete. Id {} not found", eventId);
        return false;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.debug("Getting events for day {}, pageSize {}, pageNum {}", day, pageSize, pageNum);
        return storage.getEventMap().values()
                .stream()
                .filter(entry -> entry.getDate().equals(day))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}

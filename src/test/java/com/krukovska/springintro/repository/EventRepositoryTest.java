package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.dto.EventDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventRepositoryTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private EventRepository eventRepository;

    @Test
    void getEventByIdExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());

        Event result = eventRepository.getEventById(2L);
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Game", result.getTitle());
    }

    @Test
    void getEventByNotExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());
        assertNull(eventRepository.getEventById(3L));
    }

    @Test
    void getEventByTitleExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());

        Event result = eventRepository.getEventByTitle("Game");
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Game", result.getTitle());
    }

    @Test
    void getEventByTitleNotExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());
        assertNull(eventRepository.getEventByTitle("Wedding"));
    }

    @Test
    void createEventIdNotExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());

        Event event = eventRepository.createEvent(new EventDto(3L, "Game", new Date()));

        assertNotNull(event);
        assertEquals(3L, event.getId());
    }

    @Test
    void createEventIdExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());
        assertNull(eventRepository.createEvent(new EventDto(1L, "Game", new Date())));
    }

    @Test
    void updateExistingEvent() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());

        Event event = eventRepository.updateEvent(new EventDto(1L, "Justin Bieber Concert", new Date()));

        assertNotNull(event);
        assertEquals(1L, event.getId());
        assertEquals("Justin Bieber Concert", event.getTitle());
    }

    @Test
    void updateNotExistingEvent() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());

        Event event = eventRepository.updateEvent(new EventDto(3L, "Justin Bieber Concert", new Date()));

        assertNull(event);
        assertEquals(2, storage.getEventMap().size());
    }

    @Test
    void deleteEventExists() {
        when(storage.getEventMap()).thenReturn(getDefaultTestMap());
        assertTrue(eventRepository.deleteEvent(2L));
    }

    @Test
    void deleteEventNotExists() {
        when(storage.getEventMap()).thenReturn(new HashMap<>());
        assertFalse(eventRepository.deleteEvent(2L));
    }

    @Test
    void getEventsByTitleExistsPageable() {
        when(storage.getEventMap()).thenReturn(getBigTestMap());

        List<Event> page1 = eventRepository.getEventsByTitle("Game", 1, 1);
        assertEquals(1, page1.size());
        assertEquals(2L, page1.get(0).getId());

        List<Event> page2 = eventRepository.getEventsByTitle("Game", 1, 2);
        assertEquals(1, page2.size());
        assertEquals(6L, page2.get(0).getId());
    }

    @Test
    void getEventsByTitleNotExistsPageable() {
        when(storage.getEventMap()).thenReturn(getBigTestMap());

        List<Event> result = eventRepository.getEventsByTitle("Competition", 1, 1);
        assertEquals(0, result.size());
    }

    @Test
    void getEventsForDateExistsPageable() {
        when(storage.getEventMap()).thenReturn(getBigTestMap());

        List<Event> page1 = eventRepository.getEventsForDay(new Date(2021, Calendar.OCTOBER, 12), 1, 1);
        assertEquals(1, page1.size());
        assertEquals(2L, page1.get(0).getId());

        List<Event> page2 = eventRepository.getEventsForDay(new Date(2021, Calendar.OCTOBER, 12), 1, 2);
        assertEquals(1, page2.size());
        assertEquals(7L, page2.get(0).getId());
    }

    @Test
    void getEventsForDateNotExistsPageable() {
        when(storage.getEventMap()).thenReturn(getBigTestMap());

        List<Event> result = eventRepository.getEventsForDay(new Date(2021, Calendar.AUGUST, 12), 1, 1);
        assertEquals(0, result.size());
    }

    private Map<Long, Event> getDefaultTestMap() {
        Map<Long, Event> eventMap = new HashMap<>();
        eventMap.put(1L, new EventDto(1L, "Concert", new Date()));
        eventMap.put(2L, new EventDto(2L, "Game", new Date()));
        return eventMap;
    }

    private Map<Long, Event> getBigTestMap() {
        Map<Long, Event> eventMap = new HashMap<>();
        eventMap.put(1L, new EventDto(1L, "Concert", new Date()));
        eventMap.put(2L, new EventDto(2L, "Game", new Date(2021, Calendar.OCTOBER, 12)));
        eventMap.put(3L, new EventDto(3L, "Wedding", new Date()));
        eventMap.put(4L, new EventDto(4L, "Graduation", new Date(2021, Calendar.NOVEMBER, 29)));
        eventMap.put(5L, new EventDto(5L, "Race", new Date()));
        eventMap.put(6L, new EventDto(6L, "Game", new Date()));
        eventMap.put(7L, new EventDto(7L, "Concert", new Date(2021, Calendar.OCTOBER, 12)));
        return eventMap;
    }

}
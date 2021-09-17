package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.dto.EventDto;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setMockStorage() {
        when(storage.getEventMap()).thenReturn(getTestMap());
    }

    @Test
    void getEventByIdExists() {
        Event result = eventRepository.getEventById(2);
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Game", result.getTitle());
    }

    @Test
    void getEventByNotExists() {
        assertNull(eventRepository.getEventById(30));
    }

    @Test
    void getEventByTitleExists() {
        Event result = eventRepository.getEventByTitle("Game");
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Game", result.getTitle());
    }

    @Test
    void getEventByTitleNotExists() {
        assertNull(eventRepository.getEventByTitle("Funeral"));
    }

    @Test
    void createEventIdNotExists() {
        Event event = eventRepository.createEvent(new EventDto(30, "Game", new Date()));
        assertNotNull(event);
        assertEquals(30, event.getId());
    }

    @Test
    void createEventIdExists() {
        assertNull(eventRepository.createEvent(new EventDto(1, "Game", new Date())));
    }

    @Test
    void updateExistingEvent() {
        Event event = eventRepository.updateEvent(new EventDto(1, "Justin Bieber Concert", new Date()));
        assertNotNull(event);
        assertEquals(1, event.getId());
        assertEquals("Justin Bieber Concert", event.getTitle());
    }

    @Test
    void updateNotExistingEvent() {
        Event event = eventRepository.updateEvent(new EventDto(30, "Justin Bieber Concert", new Date()));
        assertNull(event);
        assertEquals(7, storage.getEventMap().size());
    }

    @Test
    void deleteEventExists() {
        assertTrue(eventRepository.deleteEvent(2));
        assertEquals(6, storage.getEventMap().size());
    }

    @Test
    void deleteEventNotExists() {
        assertFalse(eventRepository.deleteEvent(30));
    }

    @Test
    void getEventsByTitleExistsPageable() {
        List<Event> page1 = eventRepository.getEventsByTitle("Game", 1, 1);
        assertEquals(1, page1.size());
        assertEquals(2, page1.get(0).getId());

        List<Event> page2 = eventRepository.getEventsByTitle("Game", 1, 2);
        assertEquals(1, page2.size());
        assertEquals(6, page2.get(0).getId());
    }

    @Test
    void getEventsByTitleNotExistsPageable() {
        List<Event> result = eventRepository.getEventsByTitle("Competition", 1, 1);
        assertEquals(0, result.size());
    }

    @Test
    void getEventsForDateExistsPageable() {
        List<Event> page1 = eventRepository.getEventsForDay(new Date(2021, Calendar.OCTOBER, 12), 1, 1);
        assertEquals(1, page1.size());
        assertEquals(2, page1.get(0).getId());

        List<Event> page2 = eventRepository.getEventsForDay(new Date(2021, Calendar.OCTOBER, 12), 1, 2);
        assertEquals(1, page2.size());
        assertEquals(7, page2.get(0).getId());
    }

    @Test
    void getEventsForDateNotExistsPageable() {
        List<Event> result = eventRepository.getEventsForDay(new Date(2021, Calendar.AUGUST, 12), 1, 1);
        assertEquals(0, result.size());
    }


    private Map<Long, Event> getTestMap() {
        Map<Long, Event> eventMap = new HashMap<>();
        eventMap.put(1L, new EventDto(1, "Concert", new Date()));
        eventMap.put(2L, new EventDto(2, "Game", new Date(2021, Calendar.OCTOBER, 12)));
        eventMap.put(3L, new EventDto(3, "Wedding", new Date()));
        eventMap.put(4L, new EventDto(4, "Graduation", new Date(2021, Calendar.NOVEMBER, 29)));
        eventMap.put(5L, new EventDto(5, "Race", new Date()));
        eventMap.put(6L, new EventDto(6, "Game", new Date()));
        eventMap.put(7L, new EventDto(7, "Concert", new Date(2021, Calendar.OCTOBER, 12)));
        return eventMap;
    }

}
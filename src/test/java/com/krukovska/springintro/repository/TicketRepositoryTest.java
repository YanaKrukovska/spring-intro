package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.dto.EventDto;
import com.krukovska.springintro.model.dto.TicketDto;
import com.krukovska.springintro.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.krukovska.springintro.model.Ticket.Category.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketRepositoryTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private TicketRepository ticketRepository;

    @BeforeEach
    void setMockStorage() {
        when(storage.getTicketMap()).thenReturn(getTestMap());
    }

    @Test
    void bookEmptySeat() {
        Ticket ticket = ticketRepository.bookTicket(2, 100, 2, BAR);
        assertNotNull(ticket);
        assertEquals(5, ticket.getId());
    }

    @Test
    void tryBookNotEmptySeat() {
        Ticket ticket = ticketRepository.bookTicket(2, 1, 2, BAR);
        assertNull(ticket);
        assertEquals(4, storage.getTicketMap().size());
    }

    @Test
    void getUserBookedTicketsExist() {
        List<Ticket> ticketsPage1 = ticketRepository.getBookedTickets(new UserDto(1L, "JohnDoe", "JohnDoe@gmail.com"), 1, 1);
        assertEquals(1, ticketsPage1.size());

        Ticket ticket1 = ticketsPage1.get(0);
        assertEquals(1L, ticket1.getId());
        assertEquals(1L, ticket1.getEventId());
        assertEquals(BAR, ticket1.getCategory());
        assertEquals(1, ticket1.getPlace());

        List<Ticket> ticketsPage2 = ticketRepository.getBookedTickets(new UserDto(1L, "John Doe", "JohnDoe@gmail.com"), 1, 2);
        assertEquals(1, ticketsPage2.size());

        Ticket ticket2 = ticketsPage2.get(0);
        assertEquals(4L, ticket2.getId());
        assertEquals(2L, ticket2.getEventId());
        assertEquals(STANDARD, ticket2.getCategory());
        assertEquals(20, ticket2.getPlace());
    }

    @Test
    void getUserBookedTicketsNotExist() {
        List<Ticket> tickets = ticketRepository.getBookedTickets(new UserDto(3, "Justin Bieber", "JohnDoe@gmail.com"), 1, 2);
        assertEquals(0, tickets.size());
    }

    @Test
    void getEventBookedTicketsExist() {
        List<Ticket> ticketsPage1 = ticketRepository.getBookedTickets(new EventDto(1, "Concert", new Date()), 1, 1);
        assertEquals(1, ticketsPage1.size());

        Ticket ticket1 = ticketsPage1.get(0);
        assertEquals(1L, ticket1.getId());
        assertEquals(1L, ticket1.getUserId());
        assertEquals(BAR, ticket1.getCategory());
        assertEquals(1, ticket1.getPlace());

        List<Ticket> ticketsPage2 = ticketRepository.getBookedTickets(new EventDto(1, "Concert", new Date()), 1, 2);
        assertEquals(1, ticketsPage2.size());

        Ticket ticket2 = ticketsPage2.get(0);
        assertEquals(3L, ticket2.getId());
        assertEquals(2L, ticket2.getUserId());
        assertEquals(BAR, ticket2.getCategory());
        assertEquals(2, ticket2.getPlace());
    }

    @Test
    void getEventBookedTicketsNotExist() {
        List<Ticket> tickets = ticketRepository.getBookedTickets(new EventDto(3, "Race", new Date()), 1, 2);
        assertEquals(0, tickets.size());
    }

    @Test
    void cancelExistingTicket() {
        assertTrue(ticketRepository.cancelTicket(1));
    }

    @Test
    void cancelNotExistingTicket() {
        assertFalse(ticketRepository.cancelTicket(100));
    }


    private Map<Long, Ticket> getTestMap() {
        Map<Long, Ticket> ticketMap = new HashMap<>();
        ticketMap.put(1L, new TicketDto(1, 1, 1, BAR, 1));
        ticketMap.put(2L, new TicketDto(2, 2, 2, PREMIUM, 13));
        ticketMap.put(3L, new TicketDto(3, 1, 2, BAR, 2));
        ticketMap.put(4L, new TicketDto(4, 2, 1, STANDARD, 20));
        return ticketMap;
    }

}
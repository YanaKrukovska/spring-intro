package com.krukovska.springintro.facade.impl;

import com.krukovska.springintro.facade.BookingFacade;
import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import com.krukovska.springintro.model.dto.EventDto;
import com.krukovska.springintro.model.dto.UserDto;
import com.krukovska.springintro.repository.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookingFacadeImplIT {

    @Test
    void createUserEventBookTicketAndCancelSuccess() {
        ApplicationContext context = new FileSystemXmlApplicationContext("C:\\IdeaProjects\\spring-intro\\src\\main\\resources\\app-config.xml");
        BookingFacade bookingFacade = context.getBean(BookingFacadeImpl.class);
        Storage storage = context.getBean(Storage.class);

        User createdUser = bookingFacade.createUser(new UserDto(10, "Oleg Vynnyk", "OlegVynnyk@gmail.com"));
        assertNotNull(createdUser);
        assertEquals(3, storage.getUserMap().size());

        Event createdEvent = bookingFacade.createEvent(new EventDto(10, "F1 race", new Date(2021, Calendar.OCTOBER, 4)));
        assertNotNull(createdEvent);
        assertEquals(4, storage.getEventMap().size());

        Ticket createdTicket = bookingFacade.bookTicket(10, 10, 1, Ticket.Category.STANDARD);
        assertNotNull(createdTicket);
        assertEquals(4, createdTicket.getId());
        assertEquals(4, storage.getTicketMap().size());

        assertTrue(bookingFacade.cancelTicket(4));
        assertEquals(3, storage.getTicketMap().size());
    }

}
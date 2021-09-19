package com.krukovska.springintro.facade.impl;

import com.krukovska.springintro.facade.BookingFacade;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingFacadeImplTest {

    @Test
    void test(){
        ApplicationContext context = new FileSystemXmlApplicationContext("C:\\IdeaProjects\\spring-intro\\src\\main\\resources\\app-config.xml");
        BookingFacade bookingFacade = context.getBean(BookingFacadeImpl.class);
        assertNotNull(bookingFacade);
    }

}
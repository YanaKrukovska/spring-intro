package com.krukovska.springintro;

import com.krukovska.springintro.facade.BookingFacade;
import com.krukovska.springintro.facade.impl.BookingFacadeImpl;
import com.krukovska.springintro.model.dto.UserDto;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.FileSystemXmlApplicationContext;

@SpringBootApplication
public class SpringIntroApplication {

    public static void main(String[] args) {
        var context = new FileSystemXmlApplicationContext("C:\\IdeaProjects\\spring-intro\\src\\main\\resources\\app-config.xml");
        BookingFacade bookingFacade = (BookingFacadeImpl) context.getBean("bookingFacade");
        bookingFacade.createUser(new UserDto(10, "Name", "Email"));
        System.out.println(bookingFacade.getBookedTickets(new UserDto(1, "", ""), 1, 1));
    }

}

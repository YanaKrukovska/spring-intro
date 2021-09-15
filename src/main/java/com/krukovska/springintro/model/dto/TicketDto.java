package com.krukovska.springintro.model.dto;

import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;

public class TicketDto implements Ticket {

    private long id;
    private Event event;
    private User user;
    private Category category;
    private int place;

    public TicketDto() {
    }

    public TicketDto(long id, Event event, User user, Category category, int place) {
        this.id = id;
        this.event = event;
        this.user = user;
        this.category = category;
        this.place = place;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return event.getId();
    }

    @Override
    public void setEventId(long eventId) {
        this.event.setId(eventId);
    }

    @Override
    public long getUserId() {
        return user.getId();
    }

    @Override
    public void setUserId(long userId) {
        this.user.setId(userId);
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }
}

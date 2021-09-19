package com.krukovska.springintro.model.dto;

import com.krukovska.springintro.model.User;

public class UserDto implements User {

    private long id;
    private String name;
    private String email;

    public UserDto() {
    }

    public UserDto(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User {id=" + id + ", name=" + name + ", email='" + email + '}';
    }
}

package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public User getUserById(long userId) {
        return storage.getUserMap().get(userId);
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return null;
    }

    public User createUser(User user) {
        return null;
    }

    public User updateUser(User user) {
        return null;
    }

    public boolean deleteUser(long userId) {
        return false;
    }
}

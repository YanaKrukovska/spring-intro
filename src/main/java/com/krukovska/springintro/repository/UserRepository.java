package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return storage.getUserMap().values()
                .stream()
                .filter(entry -> entry.getEmail().contains(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return storage.getUserMap().values()
                .stream()
                .filter(entry -> entry.getName().equals(name))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        Map<Long, User> userMap = storage.getUserMap();

        long eventId = user.getId();
        if (userMap.containsKey(eventId) || isEmailUsed(user.getEmail())) {
            return null;
        }
        userMap.put(eventId, user);
        return user;
    }

    public User updateUser(User user) {
        Map<Long, User> userMap = storage.getUserMap();

        long userId = user.getId();
        if (!userMap.containsKey(userId) || isEmailUsed(user.getEmail())) {
            return null;
        }
        userMap.put(userId, user);
        return user;
    }

    public boolean deleteUser(long userId) {
        Map<Long, User> userMap = storage.getUserMap();

        if (userMap.containsKey(userId)) {
            userMap.remove(userId);
            return true;
        }
        return false;
    }

    private boolean isEmailUsed(String email) {
        return storage.getUserMap().values().stream().anyMatch(entry -> entry.getEmail().equals(email));
    }

}

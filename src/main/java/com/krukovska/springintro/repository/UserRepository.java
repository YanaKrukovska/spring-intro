package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class UserRepository {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public User getUserById(long userId) {
        log.debug("Getting user with id {}", userId);
        return storage.getUserMap().get(userId);
    }

    public User getUserByEmail(String email) {
        log.debug("Getting user with email {}", email);
        return storage.getUserMap().values()
                .stream()
                .filter(entry -> entry.getEmail().contains(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.debug("Getting events with name {}, pageSize {}, pageNum {}", name, pageSize, pageNum);
        return storage.getUserMap().values()
                .stream()
                .filter(entry -> entry.getName().equals(name))
                .skip((long) pageSize * (pageNum - 1))
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        Map<Long, User> userMap = storage.getUserMap();

        long userId = user.getId();
        if (userMap.containsKey(userId) || isEmailUsed(user.getEmail())) {
            log.debug("Couldn't create new user. Id {} or email {} are already in use", userId, user.getEmail());
            return null;
        }

        userMap.put(userId, user);
        log.debug("Created new user {}", user);
        return user;
    }

    public User updateUser(User user) {
        Map<Long, User> userMap = storage.getUserMap();

        long userId = user.getId();
        if (!userMap.containsKey(userId)) {
            log.debug("Couldn't update user. User with id {} doesn't exist", userId);
            return null;
        }
        if (isEmailUsed(user.getEmail())) {
            log.debug("Couldn't update user. Email {} is already in use by another user", user.getEmail());
            return null;
        }
        userMap.put(userId, user);
        log.debug("Updated user with id {}, updated user {}", userId, user);
        return user;
    }

    public boolean deleteUser(long userId) {
        Map<Long, User> userMap = storage.getUserMap();

        if (userMap.containsKey(userId)) {
            userMap.remove(userId);
            log.debug("Deleted user with id {}", userId);
            return true;
        }
        log.debug("Couldn't find user to delete. Id {} not found", userId);
        return false;
    }

    private boolean isEmailUsed(String email) {
        return storage.getUserMap().values().stream().anyMatch(entry -> entry.getEmail().equals(email));
    }

}

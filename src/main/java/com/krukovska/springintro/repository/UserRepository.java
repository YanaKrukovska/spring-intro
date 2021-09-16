package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
        List<User> allUsers = storage.getUserMap().values()
                .stream()
                .filter(entry -> entry.getName().equals(name))
                .collect(Collectors.toList());

        return getPage(allUsers, pageSize, pageNum);
    }

    public User createUser(User user) {
        Map<Long, User> userMap = storage.getUserMap();

        long eventId = user.getId();
        if (userMap.containsKey(eventId)) {
            return null;
        }
        userMap.put(eventId, user);
        return user;
    }

    public User updateUser(User user) {
        Map<Long, User> userMap = storage.getUserMap();

        long userId = user.getId();
        if (!userMap.containsKey(userId)) {
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

    private List<User> getPage(List<User> entities, int pageSize, int pageNum) {
        int start = (pageNum - 1) * pageSize;
        if (entities == null || entities.size() <= start) {
            return Collections.emptyList();
        }

        return entities.subList(start, Math.min(start + pageSize, entities.size()));
    }
}

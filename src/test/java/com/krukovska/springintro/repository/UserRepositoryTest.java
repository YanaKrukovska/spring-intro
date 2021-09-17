package com.krukovska.springintro.repository;

import com.krukovska.springintro.model.User;
import com.krukovska.springintro.model.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private Storage storage;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setMockStorage(){
        when(storage.getUserMap()).thenReturn(getTestMap());
    }

    @Test
    void findUserByIdExists() {
        User result = userRepository.getUserById(2L);
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Jane Doe", result.getName());
        assertEquals("JaneDoe@gmail.com", result.getEmail());
    }

    @Test
    void findUserByIdNotExists() {
        assertNull(userRepository.getUserById(30L));
    }

    @Test
    void findUserByEmailExists() {
        User result = userRepository.getUserByEmail("JaneDoe@gmail.com");
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Jane Doe", result.getName());
        assertEquals("JaneDoe@gmail.com", result.getEmail());
    }

    @Test
    void findUserByEmailNotExists() {
        assertNull(userRepository.getUserByEmail("OlegVynnyk@gmail.com"));
    }

    @Test
    void createUserIdAndEmailNotExist() {
        User user = userRepository.createUser(new UserDto(5L, "Justin Bieber", "JB@gmail.com"));

        assertNotNull(user);
        assertEquals(5L, user.getId());
        assertEquals(5, storage.getUserMap().size());
    }

    @Test
    void createUserIdExists() {
        assertNull(userRepository.createUser(new UserDto(1L, "Justin Bieber", "JustinBieber@gmail.com")));
        assertEquals(4, storage.getUserMap().size());
    }

    @Test
    void createUserEmailExists() {
        assertNull(userRepository.createUser(new UserDto(5L, "Justin Bieber", "JohnDoe@gmail.com")));
        assertEquals(4, storage.getUserMap().size());
    }

    @Test
    void updateExistingUser() {
        User user = userRepository.updateUser(new UserDto(1L, "Jonathan Doe", "jd@gmail.com"));
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Jonathan Doe", user.getName());
        assertEquals("jd@gmail.com", user.getEmail());
    }

    @Test
    void updateNotExistingUser() {
        User user = userRepository.updateUser(new UserDto(30L, "Justin Bieber", "jb@gmail.com"));
        assertNull(user);
        assertEquals(4, storage.getUserMap().size());
    }

    @Test
    void setUsedEmailToUpdatedUser() {
        assertNull(userRepository.updateUser(new UserDto(3L, "Jonathan Doe", "JaneDoe@gmail.com")));
    }

    @Test
    void deleteUserExists() {
        assertTrue(userRepository.deleteUser(2L));
        assertEquals(3, storage.getUserMap().size());
    }

    @Test
    void deleteUserNotExists() {
        assertFalse(userRepository.deleteUser(30L));
        assertEquals(4, storage.getUserMap().size());
    }

    @Test
    void getUsersByNameExistsPageable() {
        List<User> page1 = userRepository.getUsersByName("John Doe", 1, 1);
        assertEquals(1, page1.size());
        assertEquals(1L, page1.get(0).getId());

        List<User> page2 = userRepository.getUsersByName("John Doe", 1, 2);
        assertEquals(1, page2.size());
        assertEquals(4L, page2.get(0).getId());
    }

    @Test
    void getEventsForDateNotExistsPageable() {
        List<User> result = userRepository.getUsersByName("Oleg Vynnyk", 1, 1);
        assertEquals(0, result.size());
    }

    private Map<Long, User> getTestMap() {
        Map<Long, User> eventMap = new HashMap<>();
        eventMap.put(1L, new UserDto(1L, "John Doe", "JohnDoe@gmail.com"));
        eventMap.put(2L, new UserDto(2L, "Jane Doe", "JaneDoe@gmail.com"));
        eventMap.put(3L, new UserDto(3L, "Justin Bieber", "JustinBieber@gmail.com"));
        eventMap.put(4L, new UserDto(4L, "John Doe", "jxd@gmail.com"));
        return eventMap;
    }

}
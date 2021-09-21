package com.krukovska.springintro.repository;

import com.google.gson.Gson;
import com.krukovska.springintro.model.Event;
import com.krukovska.springintro.model.Ticket;
import com.krukovska.springintro.model.User;
import com.krukovska.springintro.model.dto.EventDto;
import com.krukovska.springintro.model.dto.TicketDto;
import com.krukovska.springintro.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Storage {

    @Value("${data.path}")
    private String filePath;

    private final Map<Long, Event> eventMap = new HashMap<>();
    private final Map<Long, Ticket> ticketMap = new HashMap<>();
    private final Map<Long, User> userMap = new HashMap<>();

    private void init() {
        var parser = new JSONParser();
        try {
            var jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            var gson = new Gson();
            parseUsers((JSONArray) jsonObject.get("users"), gson);
            parseEvents((JSONArray) jsonObject.get("events"), gson);
            parseTickets((JSONArray) jsonObject.get("tickets"), gson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseUsers(JSONArray jsonArray, Gson gson) {
        User[] users = gson.fromJson(jsonArray.toJSONString(), UserDto[].class);
        for (User user : users) {
            getUserMap().put(user.getId(), user);
        }
        log.debug("Parsed users data");
    }

    private void parseEvents(JSONArray jsonArray, Gson gson) {
        Event[] events = gson.fromJson(jsonArray.toJSONString(), EventDto[].class);
        for (Event event : events) {
            getEventMap().put(event.getId(), event);
        }
        log.debug("Parsed events data");
    }

    private void parseTickets(JSONArray jsonArray, Gson gson) {
        Ticket[] tickets = gson.fromJson(jsonArray.toJSONString(), TicketDto[].class);
        for (Ticket ticket : tickets) {
            getTicketMap().put(ticket.getId(), ticket);
        }
        log.debug("Parsed tickets data");
    }

    public Map<Long, Event> getEventMap() {
        return eventMap;
    }

    public Map<Long, Ticket> getTicketMap() {
        return ticketMap;
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }
}
package com.enigov.service;

import com.enigov.entity.Event;
import com.enigov.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime.now());
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }
}

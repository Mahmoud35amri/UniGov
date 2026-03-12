package com.enigov.repository;

import com.enigov.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime time);
}

package com.unigov.repository;

import com.unigov.entity.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PollRepository extends MongoRepository<Poll, String> {
    Optional<Poll> findByQuestion(String question);
}

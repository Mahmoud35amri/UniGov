package com.enigov.repository;

import com.enigov.entity.PollOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollOptionRepository extends MongoRepository<PollOption, String> {
}

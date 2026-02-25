package com.unigov.repository;

import com.unigov.entity.PollOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollOptionRepository extends MongoRepository<PollOption, String> {
}

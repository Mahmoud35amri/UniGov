package com.unigov.repository;

import com.unigov.entity.Decision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisionRepository extends MongoRepository<Decision, String> {
    List<Decision> findAllByOrderByDecisionDateDesc();

    List<Decision> findByCategory(String category);
}

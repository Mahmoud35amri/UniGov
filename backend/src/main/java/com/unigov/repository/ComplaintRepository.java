package com.unigov.repository;

import com.unigov.entity.Complaint;
import com.unigov.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint, String> {
    List<Complaint> findByStudent(User student);
}

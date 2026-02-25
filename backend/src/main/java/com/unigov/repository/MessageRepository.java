package com.unigov.repository;

import com.unigov.entity.Message;
import com.unigov.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    // Find all messages between two specific users (conversation history)
    @Query("{ '$or': [ { 'sender' : ?0, 'recipient' : ?1 }, { 'sender' : ?1, 'recipient' : ?0 } ] }")
    List<Message> findConversation(User u1, User u2);

    // Note: Native SQL query for participants needs to be handled differently in
    // MongoDB or via service logic
    // For now, I'll provide a placeholder or simplified version if possible,
    // but typically we'd use aggregation for this.

    // Count unread messages for a specific recipient from a specific sender
    long countByRecipientAndSenderAndIsReadFalse(User recipient, User sender);

    // Total unread messages for a recipient
    long countByRecipientAndIsReadFalse(User recipient);
}

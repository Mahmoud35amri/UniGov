package com.unigov.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "polls")
public class Poll {
    @Id
    private String id;

    @NotBlank
    private String question;

    @DBRef
    private List<PollOption> options = new ArrayList<>();

    @DBRef
    private User creator;

    private boolean active = true;

    private LocalDateTime createdAt;

    public Poll() {
    }

    public Poll(String id, String question, List<PollOption> options, User creator, boolean active,
            LocalDateTime createdAt) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.creator = creator;
        this.active = active;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PollOption> getOptions() {
        return options;
    }

    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

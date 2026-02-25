package com.unigov.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "poll_options")
public class PollOption {
    @Id
    private String id;

    @NotBlank
    private String text;

    private int votes = 0;

    @DBRef
    private Poll poll;

    public PollOption() {
    }

    public PollOption(String id, String text, int votes, Poll poll) {
        this.id = id;
        this.text = text;
        this.votes = votes;
        this.poll = poll;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}

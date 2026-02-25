package com.unigov.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "decisions")
public class Decision {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String category;

    private LocalDateTime decisionDate;

    private String status = "OFFICIAL"; // OFFICIAL, PENDING, ARCHIVED

    public Decision() {
        this.decisionDate = LocalDateTime.now();
    }

    public Decision(String title, String content, String category) {
        this();
        this.title = title;
        this.content = content;
        this.category = category;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

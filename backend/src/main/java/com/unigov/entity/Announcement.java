package com.unigov.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

@Document(collection = "announcements")
public class Announcement {
    @Id
    private String id;

    @NotBlank
    private String title;

    private String content;

    @DBRef
    private User delegate;

    private String attachmentUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    public Announcement() {
    }

    public Announcement(String id, String title, String content, User delegate, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.delegate = delegate;
        this.createdAt = createdAt;
    }

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

    public User getDelegate() {
        return delegate;
    }

    public void setDelegate(User delegate) {
        this.delegate = delegate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}

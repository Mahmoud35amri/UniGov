package com.unigov.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PollDtos {
    public static class PollRequest {
        private String question;
        private List<String> options;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }
    }

    public static class PollResponse {
        private String id;
        private String question;
        private List<OptionResponse> options;
        private boolean active;
        private LocalDateTime createdAt;

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

        public List<OptionResponse> getOptions() {
            return options;
        }

        public void setOptions(List<OptionResponse> options) {
            this.options = options;
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

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private PollResponse instance = new PollResponse();

            public Builder id(String id) {
                instance.id = id;
                return this;
            }

            public Builder question(String question) {
                instance.question = question;
                return this;
            }

            public Builder options(List<OptionResponse> options) {
                instance.options = options;
                return this;
            }

            public Builder active(boolean active) {
                instance.active = active;
                return this;
            }

            public Builder createdAt(LocalDateTime createdAt) {
                instance.createdAt = createdAt;
                return this;
            }

            public PollResponse build() {
                return instance;
            }
        }
    }

    public static class OptionResponse {
        private String id;
        private String text;
        private int votes;

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

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private OptionResponse instance = new OptionResponse();

            public Builder id(String id) {
                instance.id = id;
                return this;
            }

            public Builder text(String text) {
                instance.text = text;
                return this;
            }

            public Builder votes(int votes) {
                instance.votes = votes;
                return this;
            }

            public OptionResponse build() {
                return instance;
            }
        }
    }
}

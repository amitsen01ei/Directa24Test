package com.directa24.test.mostactiveauthors.models;

public class User {

    private final String username;

    private final Integer submissionCount;

    public User(String username, Integer submissionCount) {
        this.username = username;
        this.submissionCount = submissionCount;
    }

    public String getUsername() {
        return username;
    }

    public Integer getSubmissionCount() {
        return submissionCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", submissionCount=" + submissionCount +
                '}';
    }
}

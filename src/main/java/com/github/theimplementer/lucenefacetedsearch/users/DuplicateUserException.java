package com.github.theimplementer.lucenefacetedsearch.users;

public class DuplicateUserException extends RuntimeException {

    private final String username;

    public DuplicateUserException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

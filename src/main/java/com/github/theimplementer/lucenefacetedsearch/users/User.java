package com.github.theimplementer.lucenefacetedsearch.users;

public class User {

    private final String username;
    private final String realName;

    private User(String username, String realName) {
        this.username = username;
        this.realName = realName;
    }

    public static User user(String username, String realName) {
        return new User(username, realName);
    }

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return realName;
    }
}

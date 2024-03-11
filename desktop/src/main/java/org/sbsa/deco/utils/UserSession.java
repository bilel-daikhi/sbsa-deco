package org.sbsa.deco.utils;

public final class UserSession {

    private static UserSession instance;

    private String userName;
    private String userId;

    private UserSession(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public static UserSession getInstance(String userName, String userId) {
        if (instance == null) {
            instance = new UserSession(userName, userId);
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void cleanUserSession() {
        instance = null;
        userName = "";// or null
        userId = "";// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
package com.itismeucci;

public class Sendable {
    private String type;
    private String user;

    public Sendable() {
    }

    public Sendable(String type, String user) {
        this.type = type;
        this.user = user;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}

package com.itismeucci;

public class Sendable {
    private String type;
    private String user;
    private int status;
    private String response;
    private String target;
    private String content;

   

    public Sendable() {
    }

    public Sendable(String type, String user, int status, String response, String target, String content) {
        this.type = type;
        this.user = user;
        this.status = status;
        this.response = response;
        this.target = target;
        this.content = content;
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
}

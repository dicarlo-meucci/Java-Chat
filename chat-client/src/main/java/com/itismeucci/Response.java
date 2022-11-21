package com.itismeucci;

public class Response extends Sendable {
    private int status;
    private String response;

    public Response(int status, String response, String type, String user) {
        super(type, user);
        this.status = status;
        this.response = response;
    }

    public Response() {
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
}

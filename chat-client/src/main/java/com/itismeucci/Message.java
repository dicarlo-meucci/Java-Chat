package com.itismeucci;

public class Message extends Sendable {
    private String target;
    private String content;



    public Message() {
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

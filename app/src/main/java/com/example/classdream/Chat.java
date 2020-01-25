package com.example.classdream;

/**
 * Class that represent the message.
 */
public class Chat {
    public String message;
    public String id;
    public String profile;
    public String name;
    public String time;

    public Chat() {
    }

    public Chat(String message, String id,String profile,String name,String time) {
        this.message = message;
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

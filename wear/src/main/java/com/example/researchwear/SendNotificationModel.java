package com.example.researchwear;

class SendNotificationModel {
    private String to,body,title;

    public SendNotificationModel(String to, String body, String title) {
        this.body = body;
        this.title = title;
        this.to=to;
    }
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
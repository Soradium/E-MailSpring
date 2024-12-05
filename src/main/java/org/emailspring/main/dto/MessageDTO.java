package org.emailspring.main.dto;

public class MessageDTO {

    private String userSender;

    private String userReceiver;

    private String content;

    public MessageDTO(String userSender, String userReceiver, String content) {
        this.userSender = userSender;
        this.userReceiver = userReceiver;
        this.content = content;
    }

    public MessageDTO() {
    }

    public String getUserSender() {
        return userSender;
    }

    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }

    public String getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(String userReceiver) {
        this.userReceiver = userReceiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

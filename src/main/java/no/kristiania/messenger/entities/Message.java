package no.kristiania.messenger.entities;

import java.util.Date;

public class Message {
    private int messageId;
    private String content;
    private int senderId;
    private int receiverId;
    private Date sentDate;

    public Message(String content, int senderId, int receiverId, Date sentDate) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sentDate = sentDate;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}

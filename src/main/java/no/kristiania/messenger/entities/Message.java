package no.kristiania.messenger.entities;

import java.util.Date;

public class Message {
    private int messageId;
    private String content;
    private int senderId;
    private int messageThreadId;
    private Date sentDate;

    public Message(String content, int senderId, int messageThreadId, Date sentDate) {
        this.content = content;
        this.senderId = senderId;
        this.messageThreadId = messageThreadId;
        this.sentDate = sentDate;
    }

    public Message() {

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

    public int getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(int messageThreadId) {
        this.messageThreadId = messageThreadId;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}

package no.kristiania.messenger.entities;

public class MessageThreadMembership {
    private int userId;
    private int messageThreadId;

    public MessageThreadMembership(int userId, int groupId) {
        this.userId = userId;
        this.messageThreadId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(int messageThreadId) {
        this.messageThreadId = messageThreadId;
    }
}

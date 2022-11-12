package no.kristiania.messenger.views;

import no.kristiania.messenger.dao.MessageThreadViewDao;

public class MessageThreadView {
    public int id;
    public String topic;
    public int unreadMessages;
    public int totalMessages;
    public int messagesRead;

    public MessageThreadView() {

    }

    public MessageThreadView(int id, String topic, int unreadMessages, int totalMessages, int messagesRead) {
        this.id = id;
        this.topic = topic;
        this.unreadMessages = unreadMessages;
        this.totalMessages = totalMessages;
        this.messagesRead = messagesRead;
    }

    public MessageThreadView(int id, String topic, int unreadMessages) {
        this.id = id;
        this.topic = topic;
        this.unreadMessages = unreadMessages;
    }
}

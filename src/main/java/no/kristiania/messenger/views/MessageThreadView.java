package no.kristiania.messenger.views;

import no.kristiania.messenger.dao.MessageThreadViewDao;

public class MessageThreadView {
    public int id;
    public String topic;
    public int unreadMessages;

    public MessageThreadView() {

    }

    public MessageThreadView(int id, String topic, int unreadMessages) {
        this.id = id;
        this.topic = topic;
        this.unreadMessages = unreadMessages;
    }
}

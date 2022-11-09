package no.kristiania.messenger.entities;

public class MessageThread {
    private int id;
    private String topic;

    public MessageThread(int id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    public MessageThread() {

    }

    public MessageThread(String topic) {
        this.topic =topic;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

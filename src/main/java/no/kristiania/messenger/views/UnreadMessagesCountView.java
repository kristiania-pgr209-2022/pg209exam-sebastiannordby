package no.kristiania.messenger.views;

public class UnreadMessagesCountView {
    public int messagesUnread;
    public int messageThreadId;

    public UnreadMessagesCountView(int messagesUnread, int messageThreadId) {
        this.messagesUnread = messagesUnread;
        this.messageThreadId = messageThreadId;
    }
}

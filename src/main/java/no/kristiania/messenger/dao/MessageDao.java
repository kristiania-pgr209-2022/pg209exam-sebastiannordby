package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.views.MessageView;

import java.util.List;

public interface MessageDao {
    int newMessage(int loggedInUser, int messageThreadId, String content) throws Exception;
    List<Message> findMessagesInThread(int messageThreadId) throws Exception;
    List<MessageView> findMessageViewsInThread(int messageThreadId) throws Exception;
}

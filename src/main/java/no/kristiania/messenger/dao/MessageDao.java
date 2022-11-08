package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.Message;

import java.util.List;

public interface MessageDao {
    int sendNewMessage (String content, int loggedInUser, int receiver) throws Exception;
    List<Message> findMessagesInThread(int messageThreadId) throws Exception;
}

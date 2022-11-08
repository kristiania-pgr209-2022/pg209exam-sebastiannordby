package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.MessageThread;

import java.util.List;

public interface MessageThreadDao {
    int insert(MessageThread entity) throws Exception;
    MessageThread find(int id) throws Exception;
    List<MessageThread> list() throws Exception;
    List<MessageThread> listThreadsByUserId(int userId) throws Exception;
}

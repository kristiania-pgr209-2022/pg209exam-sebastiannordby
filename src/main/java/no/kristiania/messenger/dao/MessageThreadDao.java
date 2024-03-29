package no.kristiania.messenger.dao;

import no.kristiania.messenger.dtos.models.UserDto;
import no.kristiania.messenger.entities.MessageThread;

import java.util.List;

public interface MessageThreadDao {
    int insert(MessageThread entity) throws Exception;
    int insert(String topic, String message, int senderId, List<Integer> userReceiverIds) throws Exception;
    MessageThread find(int id) throws Exception;
    List<MessageThread> list() throws Exception;
    List<MessageThread> listThreadsByUserId(int userId) throws Exception;
}

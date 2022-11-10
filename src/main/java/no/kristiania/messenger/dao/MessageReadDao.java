package no.kristiania.messenger.dao;

import java.util.Date;

public interface MessageReadDao {
    int unReadMessages(int userId, int messageThreadId) throws Exception;
    int insert(int userId, int messageId) throws Exception;
    void update(int userId, int messageThreadId) throws Exception;
    Date find(int userId, int messageId) throws Exception;
}

package no.kristiania.messenger.dao;

import java.util.Date;

public interface MessageReadDao {
    int unReadMessages(int userId, int messageThreadId) throws Exception;
    void markMessagesInThreadAsRead(int userId, int messageThreadId) throws Exception;
    Date find(int userId, int messageId) throws Exception;
}

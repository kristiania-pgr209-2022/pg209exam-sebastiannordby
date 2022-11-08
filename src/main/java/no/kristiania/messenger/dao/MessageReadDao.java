package no.kristiania.messenger.dao;

public interface MessageReadDao {
    int insert(int userId, int messageId) throws Exception;
    void update(int id) throws Exception;
}

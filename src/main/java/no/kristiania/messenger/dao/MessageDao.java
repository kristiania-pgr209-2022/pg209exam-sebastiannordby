package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    int sendNewMessage (User entity) throws Exception;
    List findMessageBetween(User sender, User receiver) throws SQLException;
}

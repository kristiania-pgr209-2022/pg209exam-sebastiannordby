package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    int sendNewMessage (String content, int loggedInUser, int receiver) throws Exception;
    List<Message> findMessageBetween(int sender, int receiver) throws Exception;

}

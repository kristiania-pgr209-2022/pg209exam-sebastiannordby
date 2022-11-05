package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.User;

public interface MessageDao {
    int sendNewMessage (User entity) throws Exception;
}

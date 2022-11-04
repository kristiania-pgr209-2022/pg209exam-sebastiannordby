package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.User;

import java.util.List;

public interface UserDao {
    List<User> list() throws Exception;
    User find(int id) throws Exception;
    int insertUser(User entity) throws Exception;
}

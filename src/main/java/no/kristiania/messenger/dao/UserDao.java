package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> retrieveUsers() throws SQLException;
}

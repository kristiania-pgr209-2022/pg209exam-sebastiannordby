package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.entities.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private DataSource dataSource;

    @Inject
    public JdbcUserDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }

    public int insertUser(User entity) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Users (Name, EmailAddress, Nickname, Bio) values (?, ?, ?, ?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getEmailAddress());
                stmt.setString(3, entity.getNickname());
                stmt.setString(4, entity.getBio());
                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    entity.setId(generatedKeys.getInt("Id"));

                    return generatedKeys.getInt(1);
                }
            }
        }
    }

    public User retrieveSingleUser(String name) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Users where Name = ?";

            try (var statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);

                try (var rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    @Override
    public List<User> listAllUsers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Users";

            try(PreparedStatement stmt = connection.prepareStatement(sql)){
                try (ResultSet rs = stmt.executeQuery()) {
                    List<User> users = new ArrayList<>();

                    while(rs.next()){
                        users.add(readUser(rs));
                    }

                    return users;
                }
            }
        }
    }

    static User readUser(ResultSet rs) throws SQLException {
        var user = new User();

        user.setId(rs.getInt("Id"));
        user.setName(rs.getString("Name"));
        user.setEmailAddress(rs.getString("EmailAddress"));
        user.setNickname(rs.getString("Nickname"));
        user.setBio(rs.getString("Bio"));

        return user;
    }
}

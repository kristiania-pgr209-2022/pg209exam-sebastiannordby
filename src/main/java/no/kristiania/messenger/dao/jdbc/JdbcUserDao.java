package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.entities.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private DataSource dataSource;

    @Inject
    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int insertUser(User entity) throws SQLException {
        try(var connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Users (Name, EmailAddress, Nickname, Bio) values (?, ?, ?, ?)""";

            try(var stmt = connection.prepareStatement(
                sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getEmailAddress());
                stmt.setString(3, entity.getNickname());
                stmt.setString(4, entity.getBio());
                stmt.executeUpdate();

                try(var generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    var generatedKey = generatedKeys.getInt(1);

                    entity.setId(generatedKey);

                    return generatedKey;
                }
            }
        }
    }

    public User find(int id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Users where Id = ?";

            try (var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                try (var rs = statement.executeQuery()) {
                    return rs.next() ? readUser(rs) : null;
                }
            }
        }
    }


    public List<User> list() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Users";

            try(var stmt = connection.prepareStatement(sql)){
                try (var rs = stmt.executeQuery()) {
                    var users = new ArrayList<User>();

                    while(rs.next()){
                        users.add(readUser(rs));
                    }

                    return users;
                }
            }
        }
    }
    @Override
    public void updateUser(int userId, String name, String email, String nickName, String bio) throws Exception{
        try (var connection = dataSource.getConnection()) {
            var sql = """
                UPDATE Users
                SET Name = ?, EmailAddress = ?, Nickname = ?, Bio = ?
                WHERE Id = ?""";

            try (var statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, nickName);
                statement.setString(4, bio);
                statement.setInt(5, userId);

                statement.execute();
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

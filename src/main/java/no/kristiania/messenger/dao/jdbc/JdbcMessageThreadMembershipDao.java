package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageThreadMembershipDao;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.dtos.models.UserDto;
import no.kristiania.messenger.entities.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcMessageThreadMembershipDao implements MessageThreadMembershipDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageThreadMembershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(int userId, int messageThreadId) throws Exception {
        try(var connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO MessageThreadMemberships(UserId, MessageThreadId) values (?, ?)""";

            try(var stmt = connection.prepareStatement(sql)){

                stmt.setInt(1, userId);
                stmt.setInt(2, messageThreadId);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Integer> getMessageThreadIdsByUserId(int userId) throws Exception {
        try(var connection = dataSource.getConnection()) {
            var sql = """
                    SELECT MessageThreadId from MessageThreadMemberships WHERE UserId = ?""";

            try(var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);

                var result = new ArrayList<Integer>();

                try(var rs = statement.executeQuery()) {
                    while(rs.next()) {
                        result.add(rs.getInt("MessageThreadId"));
                    }

                    return result;
                }
            }
        }
    }

    @Override
    public List<Integer> getUserIdsWhichIsMembersIn(int messageThreadId) throws Exception {
        try(var connection = dataSource.getConnection()) {
            var sql = "SELECT UserId from MessageThreadMemberships WHERE MessageThreadId = ?";

            try(var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, messageThreadId);

                var result = new ArrayList<Integer>();

                try(var rs = statement.executeQuery()) {
                    while(rs.next()) {
                        result.add(rs.getInt("UserId"));
                    }

                    return result;
                }
            }
        }
    }

    @Override
    public List<User> getMembersInMessageThread(int messageThreadId) throws Exception {
        try(var connection = dataSource.getConnection()) {
            var sql = """       
                SELECT 
                    Users.Id AS Id,
                    Users.Name AS Name,
                    Users.EmailAddress AS EmailAddress,
                    Users.Nickname AS Nickname,
                    Users.Bio AS Bio 
                FROM MessageThreadMemberships 
                JOIN Users ON Users.Id = MessageThreadMemberships.UserId 
                WHERE MessageThreadId = ?""";

            try(var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, messageThreadId);

                var result = new ArrayList<User>();

                try(var rs = statement.executeQuery()) {
                    while(rs.next()) {
                        result.add(new User(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("EmailAddress"),
                            rs.getString("Nickname"),
                            rs.getString("Bio")
                        ));
                    }

                    return result;
                }
            }
        }
    }
}

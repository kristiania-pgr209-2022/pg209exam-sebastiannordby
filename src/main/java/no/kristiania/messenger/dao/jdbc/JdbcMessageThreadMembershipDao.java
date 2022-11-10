package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageThreadMembershipDao;

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
    public int insert(int userId, int messageThreadId) throws Exception {
        try(var connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO MessageThreadMemberships(UserId, MessageThreadId) values (?, ?)""";

            try(var stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setInt(1, userId);
                stmt.setInt(2, messageThreadId);

                stmt.executeUpdate();

                try(var generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    return  generatedKeys.getInt(1);
                }
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
}

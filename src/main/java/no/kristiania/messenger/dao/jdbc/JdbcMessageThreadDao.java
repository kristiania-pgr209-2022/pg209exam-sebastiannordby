package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.entities.MessageThread;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcMessageThreadDao implements MessageThreadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageThreadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int insert(MessageThread entity) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO MessageThreads (Topic) values (?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, entity.getTopic());

                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    var generatedKey = generatedKeys.getInt(1);

                    entity.setId(generatedKey);

                    return generatedKey;
                }
            }
        }
    }

    @Override
    public void insert(String topic, String message, int senderId, List<Integer> userReceiverIds) throws Exception {
        try (var connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            // Insert MessageThread
            var insertMessageThreadSql = "INSERT INTO MessageThreads(Topic) VALUES(?)";
            try (var insertMessageThreadStmt = connection
                    .prepareStatement(insertMessageThreadSql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                insertMessageThreadStmt.setString(1, topic);
                insertMessageThreadStmt.executeUpdate();

                try(ResultSet generatedKeys = insertMessageThreadStmt.getGeneratedKeys()){
                    if(!generatedKeys.next())
                        throw new IllegalStateException("MessageThreadId was not generated.");

                    var messageThreadId = generatedKeys.getInt(1);


                    // Insert MessageThreadMemberships
                    userReceiverIds.add(senderId);

                    for(var userReceiverId : userReceiverIds) {
                        var insertIntoThreadMembershipSql = "INSERT INTO MessageThreadMemberships(MessageThreadId, UserId) VALUES(?, ?)";
                        try (var membershipStmt
                                 = connection.prepareStatement(insertIntoThreadMembershipSql)) {
                            membershipStmt.setInt(1, messageThreadId);
                            membershipStmt.setInt(2, userReceiverId);
                            membershipStmt.executeUpdate();
                        }
                    }

                    // Insert Message
                    var insertMessageSql = "INSERT INTO MESSAGES(Content, SenderId, MessageThreadId, SentDate) VALUES(?, ?, ?, ?)";
                    try(var messageStmt = connection.prepareStatement(insertMessageSql)) {
                        messageStmt.setString(1, message);
                        messageStmt.setInt(2, senderId);
                        messageStmt.setInt(3, messageThreadId);
                        messageStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                        messageStmt.executeUpdate();
                    }
                }
            }

            connection.commit();
        }
    }

    public MessageThread find(int id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM MessageThreads where Id = ?";

            try (var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                try (var rs = statement.executeQuery()) {
                    return rs.next() ? readMessageThread(rs) : null;
                }
            }
        }
    }

    public List<MessageThread> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM MessageThreads";

            try(PreparedStatement stmt = connection.prepareStatement(sql)){
                try (ResultSet rs = stmt.executeQuery()) {
                    List<MessageThread> groups = new ArrayList<>();

                    while(rs.next()){
                        groups.add(readMessageThread(rs));
                    }

                    return groups;
                }
            }
        }
    }

    @Override
    public List<MessageThread> listThreadsByUserId(int userId) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            var sql = """
                SELECT MessageThreads.Topic, MessageThreads.Id FROM MessageThreadMemberships 
                JOIN MessageThreads ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id 
                WHERE UserId = ?
            """;

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    List<MessageThread> threads = new ArrayList<>();

                    while(rs.next()){
                        var messageThreadId = rs.getInt("Id");
                        var Topic =  rs.getString("Topic");

                        threads.add(new MessageThread(messageThreadId, Topic));
                    }

                    return threads;
                }
            }
        }
    }


    static MessageThread readMessageThread(ResultSet rs) throws SQLException {
        var group = new MessageThread();

        group.setId(rs.getInt("Id"));
        group.setTopic(rs.getString("Topic"));

        return group;
    }

}

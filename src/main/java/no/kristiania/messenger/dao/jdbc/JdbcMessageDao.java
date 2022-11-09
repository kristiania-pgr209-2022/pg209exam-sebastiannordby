package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.views.MessageView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class JdbcMessageDao implements MessageDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int newMessage(int loggedInUser, int messageThreadId, String content) throws SQLException {
        try(var connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Messages (Content, SenderId, MessageThreadId, SentDate) values (?, ?, ?, ?)""";

            try(var stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, content);
                stmt.setInt(2, loggedInUser);
                stmt.setInt(3, messageThreadId);
                stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();

                try(var generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    var generatedKey = generatedKeys.getInt(1);

                    return generatedKey;
                }
            }
        }
    }

    public List<Message> findMessagesInThread(int messageThreadId) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Messages WHERE MessageThreadId = ?";

            try(var statement = connection.prepareStatement(sql)){
                statement.setInt(1, messageThreadId);


                try (var rs = statement.executeQuery()) {
                    List<Message> messages = new ArrayList<>();

                    while(rs.next()){
                        messages.add(readMessage(rs));
                    }

                    return messages;
                }
            }
        }
    }

    @Override
    public List<MessageView> findMessageViewsInThread(int messageThreadId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                SELECT Messages.Id as MessageId, Content, SenderId, MessageThreadId, SentDate, Users.Nickname AS UserNickname FROM Messages 
                JOIN Users ON Users.Id = Messages.SenderId
                WHERE MessageThreadId = ?""";

            try(var statement = connection.prepareStatement(sql)){
                statement.setInt(1, messageThreadId);

                try (var rs = statement.executeQuery()) {
                    List<MessageView> messages = new ArrayList<>();

                    while(rs.next()){
                        messages.add(readMessageView(rs));
                    }

                    return messages;
                }
            }
        }
    }

    static Message readMessage(ResultSet rs) throws SQLException {
        var message = new Message();

        message.setMessageId(rs.getInt("Id"));
        message.setContent(rs.getString("Content"));
        message.setSenderId(rs.getInt("SenderId"));
        message.setMessageThreadId(rs.getInt("MessageThreadId"));
        message.setSentDate(rs.getDate("SentDate"));

        return message;
    }

    static MessageView readMessageView(ResultSet rs) throws SQLException {
        var message = new MessageView();

        message.messageId = rs.getInt("MessageId");
        message.content = rs.getString("Content");
        message.senderId = rs.getInt("SenderId");
        message.messageThreadId = rs.getInt("MessageThreadId");
        message.sentDate = rs.getDate("SentDate");
        message.userNickname = rs.getString("UserNickName");

        return message;
    }
}

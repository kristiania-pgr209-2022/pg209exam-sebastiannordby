package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.dao.MessageThreadMembershipDao;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.views.MessageView;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class JdbcMessageDao implements MessageDao {
    private DataSource dataSource;
    private MessageThreadMembershipDao messageThreadMembershipDao;
    private MessageReadDao messageReadDao;

    @Inject
    public JdbcMessageDao(DataSource dataSource,
          MessageThreadMembershipDao messageThreadMembershipDao,
          MessageReadDao messageReadDao) {
        this.dataSource = dataSource;
        this.messageThreadMembershipDao = messageThreadMembershipDao;
        this.messageReadDao = messageReadDao;
    }

    public int newMessage(int loggedInUser, int messageThreadId, String content) throws Exception {
        try(var connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Messages (Content, SenderId, MessageThreadId, SentDate) values (?, ?, ?, ?)""";

            try(var stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, content);
                stmt.setInt(2, loggedInUser);
                stmt.setInt(3, messageThreadId);
                stmt.setTimestamp(4, Timestamp.from(Instant.now()));
                stmt.executeUpdate();

                try(var generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    return generatedKeys.getInt(1);
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
        message.setSentDate(rs.getTimestamp("SentDate"));

        return message;
    }

    static MessageView readMessageView(ResultSet rs) throws SQLException {
        var message = new MessageView();

        message.messageId = rs.getInt("MessageId");
        message.content = rs.getString("Content");
        message.senderId = rs.getInt("SenderId");
        message.messageThreadId = rs.getInt("MessageThreadId");
        message.sentDate = rs.getTimestamp("SentDate").toLocalDateTime();
        message.userNickname = rs.getString("UserNickName");

        return message;
    }


}

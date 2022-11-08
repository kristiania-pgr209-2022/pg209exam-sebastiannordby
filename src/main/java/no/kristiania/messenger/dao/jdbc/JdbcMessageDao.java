package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.entities.Message;

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
    public JdbcMessageDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }

    public int newMessage(int loggedInUser, int messageThreadId, String content) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Messages (Content, SenderId, MessageThreadId, SentDate) values (?, ?, ?, ?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, content);
                stmt.setInt(2, loggedInUser);
                stmt.setInt(3, messageThreadId);
                stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    var generatedKey = generatedKeys.getInt(1);

                    return generatedKey;
                }
            }
        }
    }

    public List<Message> findMessagesInThread(int messageThreadId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Messages WHERE MessageThreadId = ?";

            try(var statement = connection.prepareStatement(sql)){
                statement.setInt(1, messageThreadId);


                try (ResultSet rs = statement.executeQuery()) {
                    List<Message> messages = new ArrayList<>();

                    while(rs.next()){
                        messages.add(readMessage(rs));
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

}

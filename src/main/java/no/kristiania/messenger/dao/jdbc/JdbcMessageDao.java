package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class JdbcMessageDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }

    public int sendNewMessage(Message entity) throws SQLException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Messages (Content, SenderId, ReceiverId, SentDate) values (?, ?, ?, ?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, entity.getContent());
                stmt.setInt(2, entity.getSenderId());
                stmt.setInt(3, entity.getReceiverId());
                stmt.setDate(4, java.sql.Date.valueOf(df.format(date)));
                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    var generatedKey = generatedKeys.getInt(1);

                    entity.setMessageId(generatedKey);

                    return generatedKey;
                }
            }
        }
    }

    public List findMessageBetween(User sender, User receiver) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Messages where SenderId= ? and ReceiverId = ?";

            try(var statement = connection.prepareStatement(sql)){
                statement.setInt(1, sender.getId());
                statement.setInt(2,receiver.getId());


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

        message.setMessageId(rs.getInt("MessageId"));
        message.setContent(rs.getString("Content"));
        message.setSenderId(rs.getInt("SenderId"));
        message.setReceiverId(rs.getInt("ReceiverId"));
        message.setSentDate(rs.getDate("SentDate"));

        return message;
    }

}

package no.kristiania.messenger.dao.jdbc;

import no.kristiania.messenger.entities.Message;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JdbcMessageDao {
    private DataSource dataSource;

    public int sendNewMessage(Message entity) throws SQLException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Messages (Content, Sender_Id, Receiver_Id, Sent_Date) values (?, ?, ?, ?)""";

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
}

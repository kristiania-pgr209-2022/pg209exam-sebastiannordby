package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageReadDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class JdbcMessageReadDao implements MessageReadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageReadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int insert(int userId, int messageId) throws Exception {
        try(var connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO MessageRead (UserId, MessageId, ReadAt) values (?, ?, ?)""";

            try(var stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setInt(1, userId);
                stmt.setInt(2, messageId);
                stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    return  generatedKeys.getInt(1);
                }
            }
        }
    }
}

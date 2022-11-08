package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageReadDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class JdbcMessageReadDao implements MessageReadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageReadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public int insert(int userId, int messageId) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO MessageRead (UserId, MessageId) values (?, ?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setInt(1, userId);
                stmt.setInt(2, messageId);

                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    return  generatedKeys.getInt(1);
                }
            }
        }
    }
    @Override
    public void update(int id) throws Exception {
        Date date = new Date(System.currentTimeMillis());
        try (var connection = dataSource.getConnection()) {
            var sql = "UPDATE MessageRead SET ReadAt = (?) WHERE Id = (?)";

            try (var statement = connection.prepareStatement(sql)) {
                statement.setDate(1, new java.sql.Date(date.getTime()));
                statement.setInt(2, id);

            }
        }
    }
}

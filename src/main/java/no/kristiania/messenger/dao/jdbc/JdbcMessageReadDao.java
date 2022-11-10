package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.entities.MessageRead;

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
    public int unReadMessages(int userId, int messageThreadId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                    SELECT count(*) FROM MessageRead 
                    Join Messages on Messages.Id = MessageRead.MessageId
                    WHERE UserId = ? AND Messages.MessageThreadId = ? AND ReadAt = null
                    """;

            try(var statement = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setInt(1, userId);
                statement.setInt(2, messageThreadId);

                try (var rs = statement.executeQuery()) {
                    return rs.getInt(1);
                }
            }
        }
    }

    @Override
    public int insert(int userId, int messageId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                INSERT INTO MessageRead (UserId, MessageId, ReadAt) values (?, ?, ?)""";

            try(var stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setInt(1, userId);
                stmt.setInt(2, messageId);
                stmt.setDate(3, null);

                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    return  generatedKeys.getInt(1);
                }
            }
        }
    }

    @Override
    public void update(int userId, int messageThreadId) throws Exception {
        try(var connection = dataSource.getConnection()){
            var sql = """
                update mr set (ReadAt) values (?) 
                FROM MessageRead mr
                join Messages m on mr.MessageId = m.id
                where mr.readAt=NULL AND m.SenderId = (?) AND m.MessageThreadId= (?)""";

            try(var stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setInt(2, userId);
                stmt.setInt(3, messageThreadId);


                stmt.executeUpdate();


                }
            }
        }


        public Date find(int userId, int messageId) throws Exception {
            try (var connection = dataSource.getConnection()) {
                var sql = "SELECT ReadAt FROM MessageRead where UserId = (?) AND MessageId = (?) LIMIT 1";

                try(var statement = connection.prepareStatement(
                        sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                    statement.setInt(1, userId);
                    statement.setInt(2, messageId);


                    try (var rs = statement.executeQuery()) {

                        if (rs.next())
                            return rs.getDate(1);
                        else
                            return null;

                        //return rs.next() ? rs.getDate("ReadAt") : null;

                    }
                }
            }
        }
}
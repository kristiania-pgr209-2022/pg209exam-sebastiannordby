package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.entities.MessageThread;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcMessageThreadDao implements MessageThreadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageThreadDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }

    public int insert(MessageThread entity) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO MessageThreads (Name) values (?)""";

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




    static MessageThread readMessageThread(ResultSet rs) throws SQLException {
        var group = new MessageThread();

        group.setId(rs.getInt("Id"));
        group.setTopic(rs.getString("Name"));

        return group;
    }

}

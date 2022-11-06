package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.GroupDao;
import no.kristiania.messenger.entities.Group;
import no.kristiania.messenger.entities.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcGroupDao implements GroupDao {
    private DataSource dataSource;

    @Inject
    public JdbcGroupDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }

    public int insertGroup(Group entity) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO Groups (GroupName) values (?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setString(1, entity.getGroupName());

                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    var generatedKey = generatedKeys.getInt(1);

                    entity.setGroupId(generatedKey);

                    return generatedKey;
                }
            }
        }
    }

    public Group findGroup(int id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Groups where GroupId = ?";

            try (var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);

                try (var rs = statement.executeQuery()) {
                    return rs.next() ? readGroup(rs) : null;
                }
            }
        }
    }

    public List<Group> listGroups() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            var sql = "SELECT * FROM Groups";

            try(PreparedStatement stmt = connection.prepareStatement(sql)){
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Group> groups = new ArrayList<>();

                    while(rs.next()){
                        groups.add(readGroup(rs));
                    }

                    return groups;
                }
            }
        }
    }




    static Group readGroup(ResultSet rs) throws SQLException {
        var group = new Group();

        group.setGroupId(rs.getInt("GroupId"));
        group.setGroupName(rs.getString("GroupName"));

        return group;
    }

}

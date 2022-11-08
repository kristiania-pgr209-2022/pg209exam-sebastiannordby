package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.GroupMembershipDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcGroupMembershipDao implements GroupMembershipDao {
    private DataSource dataSource;

    @Inject
    public JdbcGroupMembershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int insert(int userId, int groupId) throws Exception {
        try(Connection connection = dataSource.getConnection()){
            var sql = """
                INSERT INTO GroupMemberships(UserId, GroupId) values (?, ?)""";

            try(PreparedStatement stmt = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                stmt.setInt(1, userId);
                stmt.setInt(2, groupId);

                stmt.executeUpdate();

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    generatedKeys.next();
                    return  generatedKeys.getInt(1);
                }
            }
        }
    }

    @Override
    public List<Integer> getGroupIdsByUserId(int userId) throws Exception {
        try(var connection = dataSource.getConnection()) {
            var sql = """
                    SELECT GroupId from GroupMemberships WHERE UserId = ?""";

            try(var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);

                var result = new ArrayList<Integer>();

                try(var rs = statement.executeQuery()) {
                    while(rs.next()) {
                        result.add(rs.getInt("GroupId"));
                    }

                    return result;
                }
            }
        }
    }
}

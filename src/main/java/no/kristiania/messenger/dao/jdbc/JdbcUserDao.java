package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.entities.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private DataSource dataSource;

    @Inject
    public JdbcUserDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }

    public int insertUser(User entity) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            String sql = "insert into user (name, email) values (?,?)";
            try(PreparedStatement stmt = connection.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS

            )){
                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getEmail());
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();

                generatedKeys.next();

                return generatedKeys.getInt(1);
            }
        }
    }


    @Override
    public List<User> retrieveUsers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement stmt = connection.prepareStatement(
                    "select * from user"
            )){

                try (ResultSet rs = stmt.executeQuery()) {
                    List<User> users = new ArrayList<>();

                    while(rs.next()){
                        User user = new User();

                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        users.add(user);
                    }
                    return users;
                }
            }
        }
    }
}

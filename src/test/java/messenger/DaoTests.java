package messenger;

import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoTests {

    private DataSource  dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcUserDao userDao;

    @BeforeEach
    void setUp() throws Exception {
        userDao = new JdbcUserDao(dataSource);
        try(var connection = dataSource.getConnection()){
            var statement = connection.createStatement();
                    statement.executeUpdate("create table users (id serial primary key, name varchar(100), email varchar(100))");
        }

    }

    @Test
    void shouldInsertUser() throws Exception {
        User sampleUser = SampleData.sampleUser();
        userDao.insertUser(sampleUser);
        var list = userDao.listAllUsers();

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

/*
    @Test
    void shouldListUser() throws Exception{
        User sampleUser = SampleData.sampleUser();
        userDao.insertUser(sampleUser);
        assertThat(userDao.retrieveSingleUser(sampleUser.getName()))
                .usingRecursiveComparison()
                .isEqualTo(sampleUser);
                //.isNotSameAs(sampleUser);


    }

 */

}

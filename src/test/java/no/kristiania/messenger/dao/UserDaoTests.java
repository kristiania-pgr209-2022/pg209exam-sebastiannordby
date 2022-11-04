package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTests {

    private DataSource  dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcUserDao userDao;

    @BeforeEach
    void setUp() throws Exception {
        userDao = new JdbcUserDao(dataSource);
    }

    @Test
    void shouldInsertUser() throws Exception {
        User sampleUser = SampleData.sampleUser();
        userDao.insertUser(sampleUser);
        var list = userDao.listAllUsers();

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }


    @Test
    void shouldListUser() throws Exception{
        User sampleUser = SampleData.sampleUser();
        userDao.insertUser(sampleUser);
        assertThat(userDao.retrieveSingleUser(sampleUser.getName()))
                .usingRecursiveComparison()
                .isEqualTo(sampleUser)
                .isNotSameAs(sampleUser);


    }

    @Test
    void shouldRetrieveNullForMissingUser() throws SQLException {
        assertThat(userDao.retrieveSingleUser("Konstantsin")).isNull();
    }


}

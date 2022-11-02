package messenger;

import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

public class DaoTests {

    private DataSource  dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcUserDao userDao;

    @BeforeEach
    void setUp() throws Exception {
        userDao = new JdbcUserDao(dataSource);
    }


    @Test
    void shouldListUsers() throws Exception{
        User sampleUser = SampleData.sampleBook();

    }

}

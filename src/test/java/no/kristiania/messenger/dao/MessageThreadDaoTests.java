package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageThreadDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcMessageThreadDao messageThreadDao;

    @BeforeEach
    void setUp() throws Exception {
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
    }

    @Test
    void shouldInsertGroup() throws Exception {
        MessageThread sampleGroup = SampleData.sampleThread();
        messageThreadDao.insert(sampleGroup);
        var list = messageThreadDao.list();

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

    @Test
    void shouldListMessageThreads() throws Exception{
        var messageThread = SampleData.sampleThread();
        messageThreadDao.insert(messageThread);

        var messageThreadFound = messageThreadDao.find(messageThread.getId());

        assertThat(messageThreadFound)
                .usingRecursiveComparison()
                .isEqualTo(messageThread)
                .isNotSameAs(messageThread);
    }

    @Test
    void shouldRetrieveNullForMissingGroup() throws SQLException {
        assertThat(messageThreadDao.find(-1)).isNull();
    }

}

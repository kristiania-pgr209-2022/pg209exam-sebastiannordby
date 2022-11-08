package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcMessageDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageThreadDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcMessageThreadDao messageThreadDao;
    private UserDao userDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;

    @BeforeEach
    void setUp() throws Exception {
        messageDao = new JdbcMessageDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
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
    void shouldListThreadsByUserId() throws Exception {
        var sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        var receiver = SampleData.sampleUser();
        userDao.insertUser(receiver);

        var messageThread = new MessageThread("abc");

        var messageThreadId = messageThreadDao.insert(messageThread);

        messageThreadMembershipDao.insert(sender.getId(), messageThreadId);
        messageThreadMembershipDao.insert(receiver.getId(), messageThreadId);

        var messageThreads=  messageThreadDao.listThreadsByUserId(sender.getId());


        assertThat(messageThreads).isNotNull();
        assertThat(messageThreads).isNotEmpty();
        //assertThat(messageThreads).contains(messageThread);

    }

    @Test
    void shouldRetrieveNullForMissingGroup() throws SQLException {
        assertThat(messageThreadDao.find(-1)).isNull();
    }

}

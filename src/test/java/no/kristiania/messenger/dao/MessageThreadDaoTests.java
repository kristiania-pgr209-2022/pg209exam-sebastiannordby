package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.*;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

public class MessageThreadDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private MessageThreadDao messageThreadDao;
    private UserDao userDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;

    @BeforeEach
    void setUp() throws Exception {
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource, messageThreadMembershipDao, new JdbcMessageReadDao(dataSource));
    }

    @Test
    void shouldInsertThread() throws Exception {
        var messageThread = SampleData.sampleThread();
        var messageThreadId = messageThreadDao.insert(messageThread);
        var list = messageThreadDao.list();

        assertThat(list).isNotNull().isNotEmpty();
        assertThat(list.stream().map(x -> x.getId())).contains(messageThreadId);
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
        assertThat(messageThreads.stream().map(x -> x.getId())).contains(messageThreadId);
    }

    @Test
    void shouldSimulateCreationOfNewThreadWithMessage() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());

        assertThatNoException().isThrownBy(() -> {
            messageThreadDao.insert("Simulation", "This should simulate creation.",
                senderId, new ArrayList<Integer>(){{
                    add(receiverId);
                }}
            );
        });
    }

    @Test
    void shouldRetrieveNullForMissingThread() throws Exception {
        assertThat(messageThreadDao.find(-1)).isNull();
    }
}

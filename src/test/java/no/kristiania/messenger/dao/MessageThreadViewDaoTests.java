package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.*;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageThreadViewDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private MessageThreadDao messageThreadDao;
    private UserDao userDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;
    private MessageThreadViewDao messageThreadViewDao;

    @BeforeEach
    void setUp() throws Exception {
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource, messageThreadMembershipDao, new JdbcMessageReadDao(dataSource));
        messageThreadViewDao = new JdbcMessageThreadViewDao(dataSource);
    }

    @Test
    void shouldListThreadsByUserReceiver1Id() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var receiverIds = new ArrayList<Integer>(){{
            add(receiverId);
        }};

        var messageThreadId = messageThreadDao.insert(
            "Test", "Pretty accurate", senderId, receiverIds);

        messageThreadMembershipDao.insert(senderId, messageThreadId);
        messageThreadMembershipDao.insert(receiverId, messageThreadId);

        var messageThreads=  messageThreadViewDao.getListOfThreadsByRecieverId(receiverId);
        var firstMessageThread = messageThreads.stream().findFirst();

        assertThat(messageThreads).isNotNull();
        assertThat(messageThreads).isNotEmpty();
        assertThat(firstMessageThread.get()).isNotNull();
        assertThat(firstMessageThread.get().unreadMessages).isEqualTo(1);
    }

    @Test
    void shouldListThreadsByUserReceiver2Id() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var receiverIds = new ArrayList<Integer>(){{
            add(receiverId);
        }};

        var messageThreadId = messageThreadDao.insert(
                "Test", "Pretty Accurate 1", senderId, receiverIds);

        messageThreadMembershipDao.insert(senderId, messageThreadId);
        messageThreadMembershipDao.insert(receiverId, messageThreadId);

        messageDao.newMessage(senderId, messageThreadId, "Pretty Accurate 2");
        messageDao.newMessage(senderId, messageThreadId, "Pretty Accurate 3");
        messageDao.newMessage(senderId, messageThreadId, "Pretty Accurate 4");
        messageDao.newMessage(senderId, messageThreadId, "Pretty Accurate 5");

        var messageThreads=  messageThreadViewDao.getListOfThreadsByRecieverId(receiverId);
        var firstMessageThread = messageThreads.stream().findFirst();

        assertThat(messageThreads).isNotNull();
        assertThat(messageThreads).isNotEmpty();
        assertThat(firstMessageThread.get()).isNotNull();
        assertThat(firstMessageThread.get().unreadMessages).isEqualTo(5);
    }

    @Test
    void shouldListThreadsByUserSender1Id() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var receiverIds = new ArrayList<Integer>(){{
            add(receiverId);
        }};

        var messageThreadId = messageThreadDao.insert(
                "Test", "Pretty Accurate 1", senderId, receiverIds);

        messageThreadMembershipDao.insert(senderId, messageThreadId);
        messageThreadMembershipDao.insert(receiverId, messageThreadId);

        var messageThreads=  messageThreadViewDao.getListOfThreadsByRecieverId(senderId);
        var firstMessageThread = messageThreads.stream().findFirst();

        assertThat(messageThreads).isNotNull();
        assertThat(messageThreads).isNotEmpty();
        assertThat(firstMessageThread.get()).isNotNull();
        assertThat(firstMessageThread.get().unreadMessages).isEqualTo(0);
    }

    @Test
    void shouldListThreadsByUserSender2Id() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var receiverIds = new ArrayList<Integer>(){{
            add(receiverId);
        }};

        var messageThreadId = messageThreadDao.insert(
                "Test", "Pretty Accurate 1", senderId, receiverIds);

        messageThreadMembershipDao.insert(senderId, messageThreadId);
        messageThreadMembershipDao.insert(receiverId, messageThreadId);

        messageDao.newMessage(receiverId, messageThreadId, "Reciever respond 1");
        messageDao.newMessage(receiverId, messageThreadId, "Reciever respond 2");
        messageDao.newMessage(senderId, messageThreadId, "Sender respond 1");

        var messageThreads=  messageThreadViewDao.getListOfThreadsByRecieverId(senderId);
        var firstMessageThread = messageThreads.stream().findFirst();

        assertThat(messageThreads).isNotNull();
        assertThat(messageThreads).isNotEmpty();
        assertThat(firstMessageThread.get()).isNotNull();
        assertThat(firstMessageThread.get().unreadMessages).isEqualTo(2);
    }
}

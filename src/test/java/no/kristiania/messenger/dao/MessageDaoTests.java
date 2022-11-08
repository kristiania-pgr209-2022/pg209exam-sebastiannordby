package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcMessageDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private MessageDao messageDao;
    private UserDao userDao;
    private MessageThreadDao messageThreadDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;

    @BeforeEach
    void setUp() throws Exception {
        messageDao = new JdbcMessageDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
    }

    @Test
    void shouldSendMessageToDataBase() throws Exception {
        var sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        var receiver = SampleData.sampleUser();
        userDao.insertUser(receiver);


        var messageThreadId = messageThreadDao.insert(new MessageThread(""));

        messageThreadMembershipDao.insert(sender.getId(), messageThreadId);
        messageThreadMembershipDao.insert(receiver.getId(), messageThreadId);

        Message sampleMessage = SampleData.sampleMessage(sender, messageThreadId);
        messageDao.sendNewMessage(sampleMessage.getContent(), sender.getId(), messageThreadId);

        var list = messageDao.findMessagesInThread(messageThreadId);

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

    @Test
    void shouldListMessagesBetweenUsers() throws Exception {
        var sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        var receiver = SampleData.sampleUser();
        userDao.insertUser(receiver);


        var messageThreadId = messageThreadDao.insert(new MessageThread("Tets"));


        messageThreadMembershipDao.insert(sender.getId(), messageThreadId);
        messageThreadMembershipDao.insert(receiver.getId(), messageThreadId);
        Message sampleMessage = SampleData.sampleMessage(sender, messageThreadId);

        messageDao.sendNewMessage(sampleMessage.getContent(), sender.getId(), messageThreadId);

        var messages = messageDao.findMessagesInThread(messageThreadId);
        var insertedMessage = messages.get(0);

        sampleMessage.setMessageId(insertedMessage.getMessageId());

        assertThat(insertedMessage).usingRecursiveComparison();
        assertThat(insertedMessage.getContent()).isEqualTo(sampleMessage.getContent());
        assertThat(insertedMessage).isNotSameAs(sampleMessage);
    }

    @Test
    void shouldRetrieveEmptyListForNoValidUser() throws Exception {
        assertThat(messageDao.findMessagesInThread(-1)).isEmpty();
    }

}

package no.kristiania.messenger.dao;

import javassist.tools.rmi.Sample;
import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcMessageDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcMessageDao messageDao;
    private JdbcUserDao userDao;

    @BeforeEach
    void setUp() throws Exception {
        messageDao = new JdbcMessageDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
    }

    @Test
    void shouldSendMessageToDataBase() throws SQLException {
        var sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        var receiver = SampleData.sampleUser();
        userDao.insertUser(receiver);

        Message sampleMessage = SampleData.sampleMessage(sender, receiver);
        messageDao.sendNewMessage(sampleMessage.getContent(), sender.getId(), receiver.getId());

        var list = messageDao.findMessageBetween(sender.getId(), receiver.getId());

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

    @Test
    void shouldListMessagesBetweenUsers() throws SQLException {
        var sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        var receiver = SampleData.sampleUser();
        userDao.insertUser(receiver);

        Message sampleMessage = SampleData.sampleMessage(sender, receiver);
        messageDao.sendNewMessage(sampleMessage.getContent(), sender.getId(), receiver.getId());

        var messages = messageDao.findMessageBetween(sender.getId(), receiver.getId());
        var insertedMessage = messages.get(0);

        sampleMessage.setMessageId(insertedMessage.getMessageId());

        assertThat(insertedMessage).usingRecursiveComparison();
        assertThat(insertedMessage.getContent()).isEqualTo(sampleMessage.getContent());
        assertThat(insertedMessage).isNotSameAs(sampleMessage);
    }

    @Test
    void shouldRetrieveEmptyListForNoValidUser() throws SQLException {
        assertThat(messageDao.findMessageBetween(0, -1)).isEmpty();
    }

}

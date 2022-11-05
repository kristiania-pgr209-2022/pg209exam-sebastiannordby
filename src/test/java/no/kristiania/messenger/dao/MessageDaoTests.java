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
        User sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        User reciever = new User();
        while(sender.equals(reciever)){
            reciever = SampleData.sampleUser();
        }
        userDao.insertUser(reciever);

        Message sampleMessage = SampleData.sampleMessage();
        messageDao.sendNewMessage(sampleMessage);

        var list = messageDao.findMessageBetween(sender, reciever);

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }



}

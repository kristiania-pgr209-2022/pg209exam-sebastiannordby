package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcMessageDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.MessageThread;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageReadDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcUserDao messageReadDao;
    private MessageThreadMembershipDao membershipDao;
    private UserDao userDao;
    private MessageThreadDao messageThreadDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;


    @BeforeEach
    void setUp() throws Exception {
        messageReadDao = new JdbcUserDao(dataSource);
        membershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
    }

    @Test
    void shouldAddMessageReadWhenMessageIsSent() throws Exception {
        var sender = SampleData.sampleUser();
        userDao.insertUser(sender);
        var receiver = SampleData.sampleUser();
        userDao.insertUser(receiver);
        List<Integer> recieverList = new ArrayList();
        recieverList.add(receiver.getId());


        messageThreadDao.insert("abc", "message", sender.getId(), recieverList);


    }

    @Test
    void shouldRetrieveNullForMissingMessageRead() throws SQLException {
        assertThat(messageReadDao.find(-1)).isNull();
    }
}

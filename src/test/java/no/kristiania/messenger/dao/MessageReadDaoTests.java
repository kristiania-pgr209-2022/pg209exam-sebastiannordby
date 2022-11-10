package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.*;
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
    private UserDao userDao;
    private MessageThreadMembershipDao membershipDao;
    private MessageReadDao messageReadDao;
    private MessageThreadDao messageThreadDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;


    @BeforeEach
    void setUp() throws Exception {
        userDao = new JdbcUserDao(dataSource);
        membershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        messageReadDao = new JdbcMessageReadDao(dataSource);
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
        messageReadDao.insert(receiver.getId(), 1);
    }

    @Test
    void shouldRetrieveNullForMissingMessageRead() throws Exception {
        assertThat(messageReadDao.find(-1, 1)).isNull();
    }
}

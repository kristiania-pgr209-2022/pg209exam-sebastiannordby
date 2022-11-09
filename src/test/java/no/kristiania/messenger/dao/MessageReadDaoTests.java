package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;
import java.util.ArrayList;

public class MessageReadDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private MessageReadDao messageReadDao;
    private MessageThreadMembershipDao membershipDao;
    private UserDao userDao;
    private MessageThreadDao messageThreadDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;

    @BeforeEach
    void setUp() {
        messageReadDao = new JdbcMessageReadDao(dataSource);
        membershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
    }

    @Test
    void shouldAddMessageReadWhenMessageIsSent() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var receiverList = new ArrayList<Integer>() {{
            add(receiverId);
        }};

        messageThreadDao.insert("abc", "message", senderId, receiverList);
    }
}

package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.*;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        messageReadDao = new JdbcMessageReadDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource, messageThreadMembershipDao, messageReadDao);
    }

    @Test
    void shouldListAmountOfUnreadMessagesForUser() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        List<Integer> recieverList = new ArrayList(){{
            add(receiverId);
        }};

        var messageThread1Id = messageThreadDao.insert("Whats up saturday?", "Message 1", senderId, recieverList);
        messageThreadDao.insert("My birthday next year", "Message In Second Thread", senderId, recieverList);

        messageDao.newMessage(senderId, messageThread1Id, "Message 2");

        var amountOfUnreadMessages = messageReadDao.getUnreadMessageCountByUserInThread(receiverId, messageThread1Id);

        assertThat(amountOfUnreadMessages).isEqualTo(2);
    }

    @Test
    void shouldDecreaseMessageNotReadCount() throws Exception {
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var receiverList = new ArrayList<Integer>(){{
            add(receiverId);
        }};

        var messageThreadId = messageThreadDao.insert("abc", "Message 1", senderId, receiverList);
        var message2Id = messageDao.newMessage(senderId, messageThreadId, "Message 2");
        var message3Id = messageDao.newMessage(senderId, messageThreadId, "Message 3");

        messageReadDao.markMessagesInThreadAsRead(receiverId, messageThreadId);

        var amountOfUnreadMessages = messageReadDao.getUnreadMessageCountByUserInThread(receiverId, messageThreadId);

        assertThat(amountOfUnreadMessages).isEqualTo(0);
    }

//    @Test
//    void shouldMarkMessageAsRead() throws Exception {
//        var senderId = userDao.insertUser(SampleData.sampleUser());
//        var receiverId = userDao.insertUser(SampleData.sampleUser());
//        var messageThreadId = messageThreadDao.insert(new MessageThread(""));
//        var timeStamp = LocalDateTime.now();
//
//        messageThreadMembershipDao.insert(senderId, messageThreadId);
//        messageThreadMembershipDao.insert(receiverId, messageThreadId);
//        messageDao.newMessage(senderId, messageThreadId, SampleData.getSampleMessageContent());
//        messageReadDao.markMessagesInThreadAsRead(receiverId, messageThreadId);
//
//        var views = messageReadDao.getUserViewsWhichHasReadMessage(messageThreadId);
//        var receiverReadView = views
//                .stream()
//                .filter(x -> x.userId == receiverId)
//                .collect(Collectors.toList())
//                .get(0);
//
//        assertThat(views).isNotNull();
//        assertThat(views).isNotEmpty();
//        assertThat(receiverReadView).isNotNull();
//        assertThat(receiverReadView.dateRead).isAfter(timeStamp);
//    }

    @Test
    void shouldRetrieveNullForMissingMessageRead() throws Exception {
        assertThat(messageReadDao.find(-1, 1)).isNull();
    }
}

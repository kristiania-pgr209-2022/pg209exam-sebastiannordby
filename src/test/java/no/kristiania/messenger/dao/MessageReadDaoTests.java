package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.*;
import no.kristiania.messenger.entities.MessageThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageReadDaoTests {
    private UserDao userDao;
    private MessageThreadMembershipDao membershipDao;
    private MessageReadDao messageReadDao;
    private MessageThreadDao messageThreadDao;
    private MessageDao messageDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;

    @BeforeEach
    void setUp() throws Exception {
        var dataSource = InMemoryDatabase.createTestDataSource();
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

    @Test
    void shouldMarkMessageAsRead() throws Exception {
        var timeStamp = LocalDateTime.now();
        var senderId = userDao.insertUser(SampleData.sampleUser());
        var receiverId = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(new MessageThread("Test"));

        messageThreadMembershipDao.insert(senderId, messageThreadId);
        messageThreadMembershipDao.insert(receiverId, messageThreadId);
        var messageId = messageDao.newMessage(senderId, messageThreadId, SampleData.getSampleMessageContent());
        messageReadDao.markMessagesInThreadAsRead(receiverId, messageThreadId);
        messageReadDao.markMessagesInThreadAsRead(senderId, messageThreadId);

        var count = messageReadDao.getUnreadMessageCountByUserInThread(receiverId, messageId);
        var views = messageReadDao.getUserViewsWhichHasReadMessage(messageId);
        var receiverReadView = views
                .stream()
                .filter(x -> x.userId == receiverId)
                .findFirst().orElse(null);

        assertThat(count).isEqualTo(0);
        assertThat(views).isNotNull();
        assertThat(views).isNotEmpty();
        assertThat(receiverReadView).isNotNull();
        assertThat(receiverReadView.dateRead).isAfter(timeStamp);
    }

    @Test
    void shouldRetrieveNullForMissingMessageRead() throws Exception {
        assertThat(messageReadDao.find(-1, 1)).isNull();
    }
}

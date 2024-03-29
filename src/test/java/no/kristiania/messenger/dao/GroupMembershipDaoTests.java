package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.MessageThread;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupMembershipDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private MessageThreadMembershipDao membershipDao;
    private UserDao userDao;
    private MessageThreadDao messageThreadDao;

    @BeforeEach
    void setup() throws Exception {
        membershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
    }

    @Test
    void shouldInsertMemberIntoGroup() throws Exception {
        var userId = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(new MessageThread("Test Group"));
        membershipDao.insert(userId, messageThreadId);
        var groupIdsWhereUserIsMember = membershipDao.getMessageThreadIdsByUserId(userId);

        assertThat(groupIdsWhereUserIsMember).isNotNull();
        assertThat(groupIdsWhereUserIsMember.size() >= 1).isEqualTo(true);
    }

    @Test
    void shouldListUserIdsWhichIsMemberInMessageThread() throws Exception {
        var user1Id = userDao.insertUser(SampleData.sampleUser());
        var user2Id = userDao.insertUser(SampleData.sampleUser());
        var user3Id = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(new MessageThread("Test Group"));
        membershipDao.insert(user1Id, messageThreadId);
        membershipDao.insert(user2Id, messageThreadId);
        membershipDao.insert(user3Id, messageThreadId);
        var userIds = membershipDao.getUserIdsWhichIsMembersIn(messageThreadId);

        assertThat(userIds).isNotNull();
        assertThat(userIds.size() >= 1).isEqualTo(true);
        assertThat(userIds.containsAll(new ArrayList<>(){{
            add(user1Id);
            add(user2Id);
            add(user3Id);
        }}));
    }

    @Test
    void shouldListUsersWhichIsMemberInMessageThread() throws Exception {
        var user1Id = userDao.insertUser(SampleData.sampleUser());
        var user2Id = userDao.insertUser(SampleData.sampleUser());
        var user3Id = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(new MessageThread("Test Group"));
        membershipDao.insert(user1Id, messageThreadId);
        membershipDao.insert(user2Id, messageThreadId);
        membershipDao.insert(user3Id, messageThreadId);
        var users = membershipDao.getMembersInMessageThread(messageThreadId);

        assertThat(users).isNotNull();
        assertThat(users.size() >= 1).isEqualTo(true);
        assertThat(users.stream().map(x -> x.getId())
            .collect(Collectors.toList())
            .containsAll(new ArrayList<>(){{
            add(user1Id);
            add(user2Id);
            add(user3Id);
        }}));
    }
}

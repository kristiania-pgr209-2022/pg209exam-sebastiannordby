package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.MessageThread;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

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
        var userId = userDao.insertUser(new User(
        "Test", "test@shouldInsertMemberIntoGroup.com", "Testo", "Full oftest"));
        var groupId = messageThreadDao.insert(new MessageThread("Test Group"));
        var membershipId = membershipDao.insert(userId, groupId);
        var groupIdsWhereUserIsMember = membershipDao.getMessageThreadIdsByUserId(userId);

        assertThat(groupIdsWhereUserIsMember).isNotNull();
        assertThat(groupIdsWhereUserIsMember.size() >= 1).isEqualTo(true);
    }
}

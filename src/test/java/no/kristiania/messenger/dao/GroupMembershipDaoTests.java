package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.dao.jdbc.JdbcGroupDao;
import no.kristiania.messenger.dao.jdbc.JdbcGroupMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.entities.Group;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupMembershipDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private GroupMembershipDao membershipDao;
    private UserDao userDao;
    private GroupDao groupDao;

    @BeforeEach
    void setup() throws Exception {
        membershipDao = new JdbcGroupMembershipDao(dataSource);
        userDao = new JdbcUserDao(dataSource);
        groupDao = new JdbcGroupDao(dataSource);
    }

    @Test
    void shouldInsertMemberIntoGroup() throws Exception {
        var userId = userDao.insertUser(new User(
        "Test", "test@shouldInsertMemberIntoGroup.com", "Testo", "Full oftest"));

        var groupId = groupDao.insertGroup(new Group("Test Group"));

        var result = membershipDao.insert(userId, groupId);

        assertThat(result).isTrue();
    }
}

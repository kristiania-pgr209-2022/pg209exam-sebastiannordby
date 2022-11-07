package no.kristiania.messenger.dao;

import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.dao.jdbc.JdbcGroupDao;
import no.kristiania.messenger.entities.Group;
import no.kristiania.messenger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupDaoTests {
    private DataSource dataSource = InMemoryDatabase.createTestDataSource();
    private JdbcGroupDao groupDao;

    @BeforeEach
    void setUp() throws Exception {
        groupDao = new JdbcGroupDao(dataSource);
    }

    @Test
    void shouldInsertGroup() throws Exception {
        Group sampleGroup = SampleData.sampleGroup();
        groupDao.insertGroup(sampleGroup);
        var list = groupDao.listGroups();

        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

    @Test
    void shouldListUser() throws Exception{
        var sampleGroup = SampleData.sampleGroup();
        groupDao.insertGroup(sampleGroup);
        var insertedUser = groupDao.findGroup(sampleGroup.getGroupId());

        assertThat(insertedUser)
                .usingRecursiveComparison()
                .isEqualTo(sampleGroup)
                .isNotSameAs(sampleGroup);
    }

    @Test
    void shouldRetrieveNullForMissingGroup() throws SQLException {
        assertThat(groupDao.findGroup(-1)).isNull();
    }

}

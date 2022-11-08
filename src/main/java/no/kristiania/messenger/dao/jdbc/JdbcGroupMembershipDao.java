package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.GroupMembershipDao;

import javax.sql.DataSource;

public class JdbcGroupMembershipDao implements GroupMembershipDao {
    private DataSource dataSource;

    @Inject
    public JdbcGroupMembershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean insert(int userId, int groupId) {
        return false;
    }
}

package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageReadDao;

import javax.sql.DataSource;

public class JdbcMessageReadDao implements MessageReadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageReadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}

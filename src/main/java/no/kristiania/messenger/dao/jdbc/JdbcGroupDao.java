package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;

import javax.sql.DataSource;

public class JdbcGroupDao {
    private DataSource dataSource;

    @Inject
    public JdbcGroupDao(DataSource dataSource) throws Exception{
        this.dataSource = dataSource;
    }



}

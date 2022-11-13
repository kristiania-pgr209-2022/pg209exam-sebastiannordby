package no.kristiania.messenger;

import no.kristiania.messenger.dao.*;
import no.kristiania.messenger.dao.jdbc.*;
import no.kristiania.messenger.endpoints.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import javax.sql.DataSource;

public class MessengerServerConfig extends ResourceConfig {
    public MessengerServerConfig(DataSource dataSource) {
        super(
            UserEndpoint.class,
            MessageThreadEndpoint.class,
            MessageEndpoint.class,
            MessageReadEndpoint.class
        );

        register(new AbstractBinder() {
            @Override
            protected void configure() {
            bind(JdbcUserDao.class).to(UserDao.class);
            bind(JdbcMessageDao.class).to(MessageDao.class);
            bind(JdbcMessageThreadDao.class).to(MessageThreadDao.class);
            bind(JdbcMessageThreadMembershipDao.class).to(MessageThreadMembershipDao.class);
            bind(JdbcMessageReadDao.class).to(MessageReadDao.class);
            bind(JdbcMessageThreadViewDao.class).to(MessageThreadViewDao.class);
            bind(dataSource).to(DataSource.class);
            }
        });
    }
}

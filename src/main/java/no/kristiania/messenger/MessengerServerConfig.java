package no.kristiania.messenger;

import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.dao.MessageThreadMembershipDao;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.endpoints.UserEndpoint;
import no.kristiania.messenger.entities.MessageThread;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class MessengerServerConfig extends ResourceConfig {
    public MessengerServerConfig(DataSource dataSource) {
        super(UserEndpoint.class, MessageThread.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcUserDao.class).to(UserDao.class);
                bind(JdbcMessageThreadDao.class).to(MessageThreadDao.class);
                bind(JdbcMessageThreadMembershipDao.class).to(MessageThreadMembershipDao.class);
                bind(dataSource).to(DataSource.class);
            }
        });
    }
}

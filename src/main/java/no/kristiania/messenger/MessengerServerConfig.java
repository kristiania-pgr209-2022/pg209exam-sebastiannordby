package no.kristiania.messenger;

import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import no.kristiania.messenger.endpoints.UserEndpoint;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class MessengerServerConfig extends ResourceConfig {
    public MessengerServerConfig(DataSource dataSource) {
        super(UserEndpoint.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcUserDao.class).to(UserDao.class);
                bind(dataSource).to(DataSource.class);
            }
        });
    }
}

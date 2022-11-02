package no.kristiania.messenger;

import no.kristiania.messenger.endpoints.UserEndpoint;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class MessengerServerConfig extends ResourceConfig {
    public MessengerServerConfig() {
        super(UserEndpoint.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {

            }
        });
    }
}

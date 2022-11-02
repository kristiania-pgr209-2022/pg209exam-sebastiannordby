package no.kristiania.messenger;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class MessengerServer {
    private static final Logger logger = LoggerFactory.getLogger(MessengerServer.class);
    private final Server server;

    public MessengerServer(int port) {
        this.server = new Server(port);
    }

    public void start() throws Exception {
        logger.info("Starting server..");
        server.start();
        logger.info("Server started at: ", server.getURI());
    }

    public void stop() throws Exception {
        logger.info("Stopping server..");
        server.stop();
        logger.info("Server stopped..");
    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }
}

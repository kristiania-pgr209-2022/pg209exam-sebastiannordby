package no.kristiania.messenger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class ServerTest {
    protected MessengerServer server;

    @BeforeEach
    void setup() throws Exception {
        server = new MessengerServer(0, InMemoryDatabase.createTestDataSource());
        server.start();
        additionalSetup();
    }

    @AfterEach
    void tearDown() throws Exception {
        if(server != null) {
            server.stop();
        }
    }

    protected HttpURLConnection getServerConnection(String spec) throws IOException {
        var connection = new URL(server.getUrl(), spec).openConnection();
        var httpUrlConnection = (HttpURLConnection) connection;

        return httpUrlConnection;
    }

    protected void additionalSetup() {

    }
}

package messenger;


import no.kristiania.messenger.MessengerServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;


public class MessengerServerTest {
    private MessengerServer server;

    @BeforeEach
    void setup() throws Exception {
        server = new MessengerServer(0);
        server.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
    }

    @Test
    void canConnectToServer(String spec) throws IOException {
        var connection = new URL(server.getUrl(), spec).openConnection();
        var httpUrlConnection = (HttpURLConnection) connection;

        assertThat(httpUrlConnection.getResponseCode())
                .as("HTTP Response Code")
                .isEqualTo(200);
    }

    @Test
    void shouldCreateServer()  {
        assertThat(server).isNotEqualTo(null);
    }
}

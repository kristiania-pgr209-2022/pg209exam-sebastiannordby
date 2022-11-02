package messenger;


import no.kristiania.messenger.MessengerServer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;


public class MessengerServerTest {
    private MessengerServer server;

    @BeforeEach
    void setup() throws Exception {
        server = new MessengerServer(2000);
        server.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        if(server != null) {
            server.stop();
        }
    }

    @Test
    void canConnectToServer() throws Exception {
        var connection = getServerConnection("/");

        assertThat(connection.getResponseCode())
                .as("HTTP Response Code")
                .isEqualTo(200);
    }

    @Test
    void shouldCreateServer()  {
        assertThat(server).isNotEqualTo(null);
        assertThat(server.isRunning()).isEqualTo(true);
    }

    private HttpURLConnection getServerConnection(String spec) throws IOException {
        var connection = new URL(server.getUrl(), spec).openConnection();
        var httpUrlConnection = (HttpURLConnection) connection;

        return httpUrlConnection;
    }
}

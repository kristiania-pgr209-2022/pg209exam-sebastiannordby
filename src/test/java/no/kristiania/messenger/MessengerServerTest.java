package no.kristiania.messenger;


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


public class MessengerServerTest extends ServerTest {
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

    @Test
    void shouldGetServedIndexHtml() throws IOException {
        var connection = getServerConnection("/");
        var response = connection.getResponseMessage();

        AssertionsForClassTypes.assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>");
    }
}

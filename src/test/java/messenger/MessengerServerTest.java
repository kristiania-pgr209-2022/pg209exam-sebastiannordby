package messenger;


import no.kristiania.messenger.MessengerServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void shouldCreateServer()  {
        assertThat(server).isNotEqualTo(null);
    }
}

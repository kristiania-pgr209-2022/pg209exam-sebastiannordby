package messenger;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MessengerServerTest {

    @BeforeEach
    void setup() throws Exception {
        server = new messengerServer(0);
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

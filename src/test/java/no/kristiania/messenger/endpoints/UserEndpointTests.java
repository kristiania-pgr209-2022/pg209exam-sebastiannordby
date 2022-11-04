package no.kristiania.messenger.endpoints;

import no.kristiania.messenger.ServerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserEndpointTests extends ServerTest {
    @Test
    void shouldReachGetEndpoint() throws IOException {
        var connection = super.getServerConnection("/api/user");

        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getContentType()).isEqualTo("application/json");
    }
}

package no.kristiania.messenger.endpoints;

import jakarta.json.Json;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.ServerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserEndpointTests extends ServerTest {
    @Test
    void shouldReachGetEndpoint() throws IOException {
        var connection = getServerConnection("/api/user");

        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getContentType()).isEqualTo("application/json");
    }

    @Test
    void shouldInsertUser() throws IOException {
        var connection = getServerConnection("/api/user");
        var userToInsert = SampleData.sampleUser();
        var userToInsertJson = Json.createObjectBuilder()
                .add("name", userToInsert.getName())
                .add("emailAddress", userToInsert.getEmailAddress())
                .add("nickname", userToInsert.getNickname())
                .add("bio", userToInsert.getBio())
                .build()
                .toString();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(
            userToInsertJson.getBytes(StandardCharsets.UTF_8)
        );

        assertThat(connection.getContentType()).isEqualTo("application/json");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);

        assertThat(connection.getResponseMessage().contains("id"));
    }
}

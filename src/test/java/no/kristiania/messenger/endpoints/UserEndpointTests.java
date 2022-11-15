package no.kristiania.messenger.endpoints;

import jakarta.json.Json;
import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.ServerTest;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserEndpointTests extends ServerTest {

    private UserDao userDao;

    @Override
    protected void additionalSetup() {
        userDao = new JdbcUserDao(InMemoryDatabase.createTestDataSource());
    }

    @Test
    void shouldReachGetEndpoint() throws IOException {
        var connection = getServerConnection("/api/user");

        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getContentType()).isEqualTo("application/json");
    }

    @Test
    void shouldReachFindSingleEndpoint() throws IOException {
        var createUserConnection = getServerConnection("/api/user");
        var userToInsert = SampleData.sampleUser();
        var userToInsertJson = Json.createObjectBuilder()
                .add("name", userToInsert.getName())
                .add("emailAddress", userToInsert.getEmailAddress())
                .add("nickname", userToInsert.getNickname())
                .add("bio", userToInsert.getBio())
                .build()
                .toString();

        createUserConnection.setRequestMethod("POST");
        createUserConnection.setRequestProperty("Content-Type", "application/json");
        createUserConnection.setDoOutput(true);
        createUserConnection.getOutputStream().write(
                userToInsertJson.getBytes(StandardCharsets.UTF_8)
        );

        assertThat(createUserConnection.getContentType()).isEqualTo("application/json");
        assertThat(createUserConnection.getResponseCode())
                .as(createUserConnection.getResponseMessage())
                .isEqualTo(200);

        var userIdJson = IOUtils.toString(createUserConnection.getInputStream());
        var userId = Json.createReader(new StringReader(userIdJson))
                .readObject().getInt("id");

        var findByIdConnection = getServerConnection(
                String.format("api/user/%d", userId));

        var idJsonRespons = IOUtils.toString(findByIdConnection.getInputStream());

        assertThat(findByIdConnection.getResponseCode()).isEqualTo(200);
        assertThat(findByIdConnection.getContentType()).isEqualTo("application/json");
        assertThat(idJsonRespons).contains("name");
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

    @Test
    void shouldFindUser() throws Exception {
        var sampleUser = SampleData.sampleUser();
        var sampleUserId = userDao.insertUser(sampleUser);
        var connection = getServerConnection(String.format("/api/user/%d", sampleUserId));

        connection.setRequestMethod("GET");
        assertThat(connection.getContentType()).isEqualTo("application/json");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);

        assertThat(connection.getResponseMessage().contains("id"));

        var json = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        var expectedJson = Json.createObjectBuilder()
                .add("id", sampleUserId)
                .add("name", sampleUser.getName())
                .add("emailAddress", sampleUser.getEmailAddress())
                .add("nickname", sampleUser.getNickname())
                .add("bio", sampleUser.getBio())
                .build()
                .toString();

        assertThat(json).contains("id", "name", "emailAddress", "nickname", "bio");
    }
}

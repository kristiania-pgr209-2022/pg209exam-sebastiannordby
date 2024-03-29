package no.kristiania.messenger.endpoints;

import jakarta.json.Json;
import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.ServerTest;
import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.dao.MessageThreadMembershipDao;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadDao;
import no.kristiania.messenger.dao.jdbc.JdbcMessageThreadMembershipDao;
import no.kristiania.messenger.dao.jdbc.JdbcUserDao;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessageThreadEndpointTests extends ServerTest {
    private UserDao userDao;
    private MessageThreadDao messageThreadDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;

    @Override
    protected void additionalSetup() {
        var dataSource = InMemoryDatabase.createTestDataSource();
        userDao = new JdbcUserDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
    }

    @Test
    void shouldAddThread() throws Exception {
        var createUserConnection = getServerConnection("/api/message-thread");
        var senderUserId = userDao.insertUser(SampleData.sampleUser());
        var receiverUserId = userDao.insertUser(SampleData.sampleUser());
        var topic = "Hello Friend!";
        var message = "Hope you are doing well. It's been a while. How it the programming going along?";
        var receivers = new ArrayList<Integer>(){{
            add(receiverUserId);
        }};

        var receiversJsonBuilder = Json.createArrayBuilder();

        receivers.forEach(resId -> {
            receiversJsonBuilder.add(resId);
        });

        var createUserMessageThreadCommandJson = Json.createObjectBuilder()
                .add("topic", topic)
                .add("message", message)
                .add("senderId", senderUserId)
                .add("receivers", receiversJsonBuilder)
                .build()
                .toString();

        createUserConnection.setRequestMethod("POST");
        createUserConnection.setRequestProperty("Content-Type", "application/json");
        createUserConnection.setDoOutput(true);
        createUserConnection.getOutputStream().write(
            createUserMessageThreadCommandJson.getBytes(StandardCharsets.UTF_8));

        assertThat(createUserConnection.getResponseCode())
                .as(createUserConnection.getResponseMessage())
                .isEqualTo(200);
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
    void shouldReachGetAllThreadsByUserId() throws Exception {
        var userId = userDao.insertUser(SampleData.sampleUser());
        var connection = getServerConnection(
            String.format("/api/message-thread/userId/%d", userId));
        var messageThreadId = messageThreadDao.insert(SampleData.sampleThread());

        messageThreadMembershipDao.insert(userId, messageThreadId);

        var responseJson = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);

        assertThat(connection.getContentType()).isEqualTo("application/json");
        assertThat(connection.getResponseCode())
            .as(connection.getResponseMessage())
            .isEqualTo(200);
        assertThat(responseJson).contains(Integer.toString(messageThreadId));
        assertThat(responseJson).contains("id", "topic", "unreadMessages");
    }

    @Test
    void shouldGetMembersByMessageThreadId() throws Exception {
        var userId = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(SampleData.sampleThread());
        messageThreadMembershipDao.insert(userId, messageThreadId);
        var connection = getServerConnection(
                String.format("/api/message-thread/members/%d", messageThreadId));

        var responseJson = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);

        assertThat(connection.getContentType()).isEqualTo("application/json");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
        assertThat(responseJson).contains("id", "name", "emailAddress", "nickname", "bio");
    }
}

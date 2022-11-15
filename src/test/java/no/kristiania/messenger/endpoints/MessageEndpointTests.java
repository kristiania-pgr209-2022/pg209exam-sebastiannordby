package no.kristiania.messenger.endpoints;

import jakarta.json.Json;
import no.kristiania.messenger.InMemoryDatabase;
import no.kristiania.messenger.SampleData;
import no.kristiania.messenger.ServerTest;
import no.kristiania.messenger.dao.*;
import no.kristiania.messenger.dao.jdbc.*;
import no.kristiania.messenger.dtos.commands.InsertNewMessageIntoThreadCommandDto;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessageEndpointTests extends ServerTest {
    private UserDao userDao;
    private MessageThreadDao messageThreadDao;
    private MessageThreadMembershipDao messageThreadMembershipDao;
    private MessageDao messageDao;
    private MessageReadDao messageReadDao;

    @Override
    protected void additionalSetup() {
        var dataSource = InMemoryDatabase.createTestDataSource();
        userDao = new JdbcUserDao(dataSource);
        messageThreadDao = new JdbcMessageThreadDao(dataSource);
        messageThreadMembershipDao = new JdbcMessageThreadMembershipDao(dataSource);
        messageDao = new JdbcMessageDao(dataSource);
        messageReadDao = new JdbcMessageReadDao(dataSource);
    }

    @Test
    void listAllMessagesInThread() throws Exception {
        var user1Id = userDao.insertUser(SampleData.sampleUser());
        var user2Id = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(SampleData.sampleThread());
        messageThreadMembershipDao.insert(user1Id, messageThreadId);
        messageThreadMembershipDao.insert(user2Id, messageThreadId);

        var messageId = messageDao.newMessage(user1Id, messageThreadId, "shouldGetMembersByMessageThreadId");
        messageReadDao.markMessagesInThreadAsRead(user2Id, messageThreadId);

        var connection = getServerConnection(
                String.format("/api/message/%d", messageThreadId));

        var responseJson = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);

        assertThat(connection.getContentType()).isEqualTo("application/json");
        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
        assertThat(responseJson).contains("messageId", "content",
            "senderId", "messageThreadId", "sentDate", "userNickname");
        assertThat(responseJson).contains(Integer.toString(user1Id));
        assertThat(responseJson).contains(Integer.toString(messageId));
    }

    @Test
    void shouldInsertMessageIntoThread() throws Exception {
        var user1Id = userDao.insertUser(SampleData.sampleUser());
        var user2Id = userDao.insertUser(SampleData.sampleUser());
        var messageThreadId = messageThreadDao.insert(SampleData.sampleThread());
        messageThreadMembershipDao.insert(user1Id, messageThreadId);
        messageThreadMembershipDao.insert(user2Id, messageThreadId);

        var connection = getServerConnection("/api/message");
        var command = new InsertNewMessageIntoThreadCommandDto(
            user1Id, messageThreadId, "TEST.MESSAGE");

        var messageToInsertJson = Json.createObjectBuilder()
            .add("userId", command.userId)
            .add("messageThreadId", command.messageThreadId)
            .add("message", command.message)
            .build()
            .toString();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(
            messageToInsertJson.getBytes(StandardCharsets.UTF_8));

        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage())
                .isEqualTo(200);
    }
}

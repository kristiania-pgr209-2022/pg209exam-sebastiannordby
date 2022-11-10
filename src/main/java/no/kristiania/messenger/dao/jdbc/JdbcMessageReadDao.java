package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.entities.MessageRead;
import no.kristiania.messenger.views.UnreadMessagesCountView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JdbcMessageReadDao implements MessageReadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageReadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private List<Integer> getMessageIdsWhereMessagesNotReadForUser(Connection connection, int userId, int messageThreadId) throws Exception{
        var allMessageIdsForUser = new ArrayList<Integer>();
        var allMessageIdsForUserMarkedAsRead = new ArrayList<Integer>();

        // All messages in the thread that userId is connected to
        var sql = """
            SELECT Messages.Id FROM Messages
            JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
            JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
            WHERE UserId = ? AND Messages.MessageThreadId = ?""";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, messageThreadId);

            try (var rs = statement.executeQuery()) {

                while (rs.next()) {
                    allMessageIdsForUser.add(rs.getInt(1));
                }
            }
        }

        // All messages in thread which are read
        var messageReadSql = """
            SELECT Messages.Id FROM Messages
            JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
            JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
            JOIN MessageRead ON MessageRead.MessageId = Messages.Id
            WHERE MessageRead.UserId = ? AND MessageThreads.Id = ?""";

        try (var statement = connection.prepareStatement(messageReadSql)) {
            statement.setInt(1, userId);
            statement.setInt(2, messageThreadId);

            try (var rs = statement.executeQuery()) {
                while (rs.next()) {
                    allMessageIdsForUserMarkedAsRead.add(rs.getInt(1));
                }
            }
        }

        return allMessageIdsForUser
                .stream()
                .filter(messageId -> !allMessageIdsForUserMarkedAsRead.contains(messageId))
                .toList();
    }

    @Override
    public List<UnreadMessagesCountView> getUnreadMessages(int userId, List<Integer> messageThreadIds) throws Exception {
        var result = new ArrayList<UnreadMessagesCountView>();

        try (var connection = dataSource.getConnection()) {
            for (var messageThreadId : messageThreadIds) {
                var unreadMessageCountForThread = getMessageIdsWhereMessagesNotReadForUser(connection, userId, messageThreadId).size();
                var view = new UnreadMessagesCountView(unreadMessageCountForThread, messageThreadId);

                result.add(view);
            }
        }

        return result;
    }

    @Override
    public int unReadMessages(int userId, int messageThreadId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            return getMessageIdsWhereMessagesNotReadForUser(connection, userId, messageThreadId).size();
        }
    }

    @Override
    public void markMessagesInThreadAsRead(int userId, int messageThreadId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            var messageIdsToMarkAsRead =
                    getMessageIdsWhereMessagesNotReadForUser(connection, userId, messageThreadId);

            for(var messageIdToMarkAsRead : messageIdsToMarkAsRead) {
                var insertMessageReadSql = "INSERT INTO MessageRead (UserId, MessageId, ReadAt) values (?, ?, ?)";

                try(var stmt = connection.prepareStatement(insertMessageReadSql)){
                    stmt.setInt(1, userId);
                    stmt.setInt(2, messageIdToMarkAsRead);
                    stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                    stmt.executeUpdate();
                }
            }

            connection.commit();
        }
    }

    public Date find(int userId, int messageId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT ReadAt FROM MessageRead where UserId = (?) AND MessageId = (?) LIMIT 1";

            try(var statement = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1, userId);
                statement.setInt(2, messageId);


                try (var rs = statement.executeQuery()) {

                    if (rs.next())
                        return rs.getDate(1);
                    else
                        return null;
                }
            }
        }
    }
}
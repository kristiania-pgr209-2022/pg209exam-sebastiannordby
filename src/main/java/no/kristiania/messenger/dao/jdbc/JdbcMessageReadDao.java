package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.entities.MessageRead;
import no.kristiania.messenger.views.MessageReadByUserView;
import no.kristiania.messenger.views.UnreadMessagesCountView;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
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
        var allMessageIdsForUserThatWhichAreNotMarkedRead = new ArrayList<Integer>();

        var sql = """
            SELECT Messages.Id FROM Messages
            JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
            JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
            WHERE UserId = ? AND Messages.MessageThreadId = ? AND Messages.Id NOT IN(
                SELECT Messages.Id FROM Messages
                JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
                JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
                JOIN MessageRead ON MessageRead.MessageId = Messages.Id
                WHERE MessageRead.UserId = ? AND MessageThreads.Id = ?
            )""";

        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, messageThreadId);
            statement.setInt(3, userId);
            statement.setInt(4, messageThreadId);

            try (var rs = statement.executeQuery()) {
                while (rs.next()) {
                    allMessageIdsForUserThatWhichAreNotMarkedRead.add(rs.getInt(1));
                }
            }
        }

        return allMessageIdsForUserThatWhichAreNotMarkedRead;
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
    public int getUnreadMessageCountByUserInThread(int userId, int messageThreadId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            return getMessageIdsWhereMessagesNotReadForUser(connection, userId, messageThreadId).size();
        }
    }

    @Override
    public void markMessagesInThreadAsRead(int userId, int messageThreadId) throws Exception {
        try (var connection = dataSource.getConnection()) {

            var messageIdsToMarkAsRead =
                    getMessageIdsWhereMessagesNotReadForUser(connection, userId, messageThreadId);

            connection.setAutoCommit(false);

            for(var messageIdToMarkAsRead : messageIdsToMarkAsRead) {
                var insertMessageReadSql = "INSERT INTO MessageRead (UserId, MessageId, ReadAt) values (?, ?, ?)";

                try(var stmt = connection.prepareStatement(insertMessageReadSql)){
                    stmt.setInt(1, userId);
                    stmt.setInt(2, messageIdToMarkAsRead);
                    stmt.setTimestamp(3, Timestamp.from(Instant.now()));
                    stmt.executeUpdate();
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Date find(int userId, int messageId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var sql = "SELECT ReadAt FROM MessageRead where UserId = (?) AND MessageId = (?) LIMIT 1";

            try(var statement = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1, userId);
                statement.setInt(2, messageId);

                try (var rs = statement.executeQuery()) {
                    return rs.next() ? rs.getDate(1) : null;
                }
            }
        }
    }

    @Override
    public List<MessageReadByUserView> getUserViewsWhichHasReadMessage(int messageId) throws Exception {
        try (var connection = dataSource.getConnection()) {
            var sql = """       
                SELECT ReadAt, Users.Nickname AS UserNickname, Users.Id AS UserId FROM MessageRead
                LEFT OUTER JOIN Users ON Users.Id = MessageRead.UserId
                WHERE MessageId = ?""";

            try(var statement = connection.prepareStatement(
                    sql, PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1, messageId);

                try (var rs = statement.executeQuery()) {
                    var result = new ArrayList<MessageReadByUserView>();

                    while(rs.next()) {
                        result.add(new MessageReadByUserView(
                            rs.getInt("UserId"),
                            rs.getString("UserNickname"),
                            rs.getTimestamp("ReadAt").toLocalDateTime()
                        ));
                    }

                    return result;
                }
            }
        }
    }
}
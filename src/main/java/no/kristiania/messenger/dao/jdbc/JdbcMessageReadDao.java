package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.entities.MessageRead;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class JdbcMessageReadDao implements MessageReadDao {
    private DataSource dataSource;

    @Inject
    public JdbcMessageReadDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public int unReadMessages(int userId, int messageThreadId) throws Exception {
        var allMessageIdsForUser = new ArrayList<Integer>();
        var allMessageIdsForUserMarkedAsRead = new ArrayList<Integer>();

        try (var connection = dataSource.getConnection()) {

            // All messages in the thread that userId is connected to
            var sql = """
                    SELECT Messages.Id FROM Messages
                    JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
                    JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
                    WHERE UserId = ?
                    """;

            try (var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);

                try (var rs = statement.executeQuery()) {

                    while (rs.next()) {
                        allMessageIdsForUser.add(rs.getInt(1));
                    }
                }
            }

            var messageReadSql = """
                    SELECT Messages.Id FROM Messages
                    JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
                    JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
                    JOIN MessageRead ON MessageRead.MessageId = Messages.Id
                    WHERE MessageRead.UserId = ?""";

            try (var statement = connection.prepareStatement(messageReadSql)) {
                statement.setInt(1, userId);

                try (var rs = statement.executeQuery()) {
                    while (rs.next()) {
                        allMessageIdsForUserMarkedAsRead.add(rs.getInt(1));
                    }
                }
            }
        }

        return allMessageIdsForUser
                .stream()
                .filter(messageId -> !allMessageIdsForUserMarkedAsRead.contains(messageId))
                .toList()
                .size();
    }

    @Override
    public void markMessagesInThreadAsRead(int userId, int messageThreadId) throws Exception {

        var allMessageIdsForUser = new ArrayList<Integer>();
        var allMessageIdsForUserMarkedAsRead = new ArrayList<Integer>();

        try (var connection = dataSource.getConnection()) {

            // All messages in the thread that userId is connected to
            var sql = """
                    SELECT Messages.Id FROM Messages
                    JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
                    JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
                    WHERE UserId = ?
                    """;

            try (var statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);

                try (var rs = statement.executeQuery()) {

                    while (rs.next()) {
                        allMessageIdsForUser.add(rs.getInt(1));
                    }
                }
            }

            var messageReadSql = """
                    SELECT Messages.Id FROM Messages
                    JOIN MessageThreads ON MessageThreads.Id = Messages.MessageThreadId
                    JOIN MessageThreadMemberships ON MessageThreadMemberships.MessageThreadId = MessageThreads.Id
                    JOIN MessageRead ON MessageRead.MessageId = Messages.Id
                    WHERE MessageRead.UserId = ?""";

            try (var statement = connection.prepareStatement(messageReadSql)) {
                statement.setInt(1, userId);

                try (var rs = statement.executeQuery()) {
                    while (rs.next()) {
                        allMessageIdsForUserMarkedAsRead.add(rs.getInt(1));
                    }
                }
            }

            connection.setAutoCommit(false);

            var messageIdsToMarkAsRead = allMessageIdsForUser
                    .stream()
                    .filter(messageId -> !allMessageIdsForUserMarkedAsRead.contains(messageId))
                    .toList();

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

                    //return rs.next() ? rs.getDate("ReadAt") : null;

                }
            }
        }
    }
}
package no.kristiania.messenger.dao.jdbc;

import jakarta.inject.Inject;
import no.kristiania.messenger.dao.MessageThreadViewDao;
import no.kristiania.messenger.entities.MessageThread;
import no.kristiania.messenger.views.MessageThreadView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcMessageThreadViewDao implements MessageThreadViewDao {

    private DataSource dataSource;

    @Inject
    public JdbcMessageThreadViewDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<MessageThreadView> getListOfThreadsByRecieverId(int userReceiverId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            var sql = """                
                SELECT
                	MessageThreads.Id,
                	MessageThreads.Topic,
                	MessageThreadMemberships.UserId,
                	(COUNT(Messages.Id) - COUNT(MessageRead.UserId)) AS UnreadCount
                FROM MessageThreadMemberships
                	LEFT OUTER JOIN MessageThreads ON MessageThreads.Id = MessageThreadMemberships.MessageThreadId
                	LEFT OUTER JOIN Messages ON Messages.MessageThreadId = MessageThreads.Id AND Messages.SenderId != MessageThreadMemberships.UserId
                	LEFT OUTER JOIN MessageRead ON MessageRead.MessageId = Messages.Id AND MessageRead.UserId = MessageThreadMemberships.UserId
                WHERE MessageThreadMemberships.UserId = ?
                GROUP BY MessageThreadMemberships.UserId, MessageThreads.Topic, MessageThreads.Id, MessageThreadMemberships.Id
            """;

            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, userReceiverId);

                try (ResultSet rs = stmt.executeQuery()) {
                    var views = new ArrayList<MessageThreadView>();

                    while(rs.next()){
                        var messageThreadId = rs.getInt("Id");
                        var topic =  rs.getString("Topic");
                        var unreadCount =  rs.getInt("UnreadCount");

                        views.add(new MessageThreadView(
                            messageThreadId,
                            topic,
                            unreadCount
                        ));
                    }

                    return views;
                }
            }
        }
    }
}

package no.kristiania.messenger.dtos.queries;

import java.util.List;

public class MessageThreadsUnreadCountQuery {
    public List<Integer> messageThreadIds;
    public int userId;
}

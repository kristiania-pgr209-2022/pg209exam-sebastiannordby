package no.kristiania.messenger.dao;

import java.util.List;

public interface MessageThreadMembershipDao {
    int insert(int userId, int messageThreadId) throws Exception;
    List<Integer> getMessageThreadIdsByUserId(int userId) throws Exception;
    List<Integer> getUserIdsWhichIsMembersIn(int messageThreadId) throws Exception;
}

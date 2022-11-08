package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.GroupMembership;

import java.sql.SQLException;
import java.util.List;

public interface GroupMembershipDao {
    int insert(int userId, int groupId) throws Exception;
    List<Integer> getGroupIdsByUserId(int userId) throws Exception;
}

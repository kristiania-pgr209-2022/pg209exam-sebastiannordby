package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.GroupMembership;

public interface GroupMembershipDao {
    boolean insert(int userId, int groupId);
}

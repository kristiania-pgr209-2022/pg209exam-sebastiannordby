package no.kristiania.messenger.dao;

import no.kristiania.messenger.dtos.models.UserDto;
import no.kristiania.messenger.entities.User;

import java.util.List;

public interface MessageThreadMembershipDao {
    void insert(int userId, int messageThreadId) throws Exception;
    List<Integer> getMessageThreadIdsByUserId(int userId) throws Exception;
    List<Integer> getUserIdsWhichIsMembersIn(int messageThreadId) throws Exception;
    List<User> getMembersInMessageThread(int messageThreadId) throws Exception;
}

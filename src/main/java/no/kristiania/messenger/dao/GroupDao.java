package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDao {
    int insertGroup(Group entity) throws Exception;
    Group findGroup(int id) throws Exception;
    List<Group> listGroups() throws Exception;
}

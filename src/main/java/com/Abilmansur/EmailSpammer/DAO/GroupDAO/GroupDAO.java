package com.Abilmansur.EmailSpammer.DAO.GroupDAO;

import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;

import java.util.List;

public interface GroupDAO {
    List<GroupEntity> getAllGroups();
    void saveGroup(GroupEntity group);
    GroupEntity getGroup(int id);
    void deleteGroup(int id);
    void createLink(GroupEntity group, UserEntity user);

    List<GroupEntity> getSpecificGroupsDTO(String groupName);

}

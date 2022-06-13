package com.Abilmansur.EmailSpammer.DAO.UserDAO;

import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;

import java.util.List;

public interface UserDAO {
    List<UserEntity> getAllUsers();
    void saveUser(UserEntity user);
    UserEntity getUser(int id);
    void deleteUser(int id);
    void deleteLink(UserEntity user, GroupEntity group);
    public List<UserEntity> getSpecificUsers(String first, String second);
    List<UserEntity> getNonMembers(List<String> userList);
    void persistUser(UserEntity user);
}

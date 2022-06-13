package com.Abilmansur.EmailSpammer.DAO.UserDAO;

import com.Abilmansur.EmailSpammer.DAO.GroupDAO.GroupDAOImplementation;
import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class UserDAOImplementation implements UserDAO{

    @Autowired
    EntityManager entityManager;

    @Autowired
    GroupDAOImplementation groupDAO;

    @Override
    public List<UserEntity> getAllUsers() {
        Query query = entityManager.createQuery(" from UserEntity");
        List<UserEntity> allUsers = query.getResultList();
        return allUsers;
    }

    public List<UserEntity> getSpecificUsers(String first, String second) {
        Query query = entityManager.createQuery(" from UserEntity" +
                " where (name like :first and surname like :second) or (name like :second and surname like :first)");
        query.setParameter("first", "%" + first + "%").setParameter("second", "%" + second + "%");
        List<UserEntity> allUsers = query.getResultList();
        return allUsers;
    }

    @Override
    @Transactional
    public void saveUser(UserEntity user) {
        entityManager.merge(user);
    }

    @Override
    public UserEntity getUser(int id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        return user;
    }


    @Override
    public void deleteUser(int id) {
        Query query = entityManager.createQuery("delete from User" + "Entity where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteLink(UserEntity user, GroupEntity group) {
        user.removeGroup(group);
    }

    @Override
    public List<UserEntity> getNonMembers(List<String> userEmails) {
        List<UserEntity> nonMembers = null;
        if (userEmails.isEmpty()) {
            nonMembers = getAllUsers();
        } else {
            Query query = entityManager.createQuery("from UserEntity where email not in (:userEmails)");
            query.setParameter("userEmails", userEmails);
            nonMembers = query.getResultList();
        }
        return nonMembers;
    }

    @Override
    @Transactional
    public void persistUser(UserEntity user) {
        entityManager.persist(user);
    }

}



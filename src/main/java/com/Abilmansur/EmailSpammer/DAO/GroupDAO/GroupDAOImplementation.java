package com.Abilmansur.EmailSpammer.DAO.GroupDAO;

import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GroupDAOImplementation implements GroupDAO{

    @Autowired
    EntityManager entityManager;

    @Override
    public List<GroupEntity> getAllGroups() {
        Query query = entityManager.createQuery("from GroupEntity ");
        List<GroupEntity> groups = query.getResultList();
        return groups;
    }

    @Override
    @Transactional
    public void saveGroup(GroupEntity group) {
        entityManager.merge(group);
    }

    @Override
    public GroupEntity getGroup(int id) {
        GroupEntity group = entityManager.find(GroupEntity.class, id);
        return group;
    }

    @Override
    public void deleteGroup(int id) {
        Query query = entityManager.createQuery("delete from GroupEntity where id =:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void createLink(GroupEntity group, UserEntity user) {
        group.addUser(user);
    }

    @Override
    public List<GroupEntity> getSpecificGroupsDTO(String groupName) {
        Query query = entityManager.createQuery("from GroupEntity where name like :groupName");
        query.setParameter("groupName", "%" + groupName + "%");
        return query.getResultList();
    }

}

package com.Abilmansur.EmailSpammer.DAO.MessageDAO;

import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MessageDAOImplementation implements MessageDAO{

    @Autowired
    EntityManager entityManager;

    @Override
    public void saveMessage(MessageEntity message) {
        entityManager.merge(message);
    }

    @Override
    public List<MessageEntity> getAllMessages() {
        Query query = entityManager.createQuery("from MessageEntity ");
        List<MessageEntity> allMessages = query.getResultList();
        return allMessages;
    }
    @Override
    public void deleteMessage(int id) {
        entityManager.createQuery("delete from MessageEntity where id = :id").setParameter("id",id).executeUpdate();
    }

    @Override
    public MessageEntity getMessage(int id) {
        return entityManager.find(MessageEntity.class, id);
    }
}

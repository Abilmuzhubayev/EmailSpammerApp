package com.Abilmansur.EmailSpammer.DAO.MessageDAO;

import com.Abilmansur.EmailSpammer.Entity.MessageEntity;

import java.util.List;

public interface MessageDAO {
    void saveMessage(MessageEntity message);
    List<MessageEntity> getAllMessages();
    void deleteMessage(int id);
    MessageEntity getMessage(int id);

}

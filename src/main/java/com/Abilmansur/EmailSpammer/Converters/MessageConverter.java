package com.Abilmansur.EmailSpammer.Converters;

import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.DTO.UserDTO;
import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MessageConverter {

    public static MessageEntity getMessageEntity(MessageDTO messageDTO, GroupEntity group) {
        MessageEntity message = new MessageEntity();
        message.setId(messageDTO.getId());
        message.setGroup(group);
        message.setSubject(messageDTO.getSubject());
        message.setBody(messageDTO.getBody());
        message.setLocalTime(messageDTO.getLocalTime());
        return message;
    }

    public static MessageDTO getMessageDTO(MessageEntity message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setGroupId(message.getGroup().getId());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setBody(message.getBody());
        messageDTO.setId(message.getId());
        messageDTO.setLocalTime(message.getLocalTime());
        messageDTO.setGroupName(message.getGroup().getName());
        return messageDTO;
    }

    public static List<MessageDTO> getAllMessagesDTO(List<MessageEntity> messages) {
        return messages.stream().map(x -> getMessageDTO(x)).collect(Collectors.toList());
    }
}

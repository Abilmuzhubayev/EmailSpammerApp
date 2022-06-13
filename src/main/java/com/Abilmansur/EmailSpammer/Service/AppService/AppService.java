package com.Abilmansur.EmailSpammer.Service.AppService;

import com.Abilmansur.EmailSpammer.DTO.GroupDTO;
import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.DTO.UserDTO;
import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;

import java.util.List;

public interface AppService {
    List<GroupDTO> getAllGroupsDTO();
    boolean saveGroup(GroupDTO groupDTO);
    GroupEntity getGroup(int id);
    void deleteGroup(int id);
    List<UserDTO> getAllUsersDTO();
    List<UserDTO> getSpecificUsersDTO(String name);

    List<GroupDTO> getSpecificGroupsDTO(String groupName);
    boolean saveUser(UserDTO userDTO);
    UserEntity getUser(int id);
    void deleteUser(int id);
    void deleteLink(int userId, int groupId);
    void createLink(int groupId, int userId);
    boolean persistUser(UserDTO userDTO);
    void saveMessage(MessageDTO messageDTO);
    List<MessageDTO> getAllMessagesDTO();
    void deleteMessage(int id);
    UserDTO getUserDTO(int id);
    List<UserDTO> getNonMembersDTO(int id);
    GroupDTO getGroupDTO(int id);
    MessageDTO getMessageDTO(int id);
    MessageEntity getMessage(int id);

}

package com.Abilmansur.EmailSpammer.Service.AppService;

import com.Abilmansur.EmailSpammer.Converters.GroupConverter;
import com.Abilmansur.EmailSpammer.Converters.MessageConverter;
import com.Abilmansur.EmailSpammer.Converters.UserConverter;
import com.Abilmansur.EmailSpammer.DAO.GroupDAO.GroupDAOImplementation;
import com.Abilmansur.EmailSpammer.DAO.MessageDAO.MessageDAOImplementation;
import com.Abilmansur.EmailSpammer.DAO.UserDAO.UserDAOImplementation;
import com.Abilmansur.EmailSpammer.DTO.GroupDTO;
import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.DTO.UserDTO;
import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppServiceImplementation implements AppService{

    @Autowired
    UserDAOImplementation userDAO;

    @Autowired
    GroupDAOImplementation groupDAO;

    @Autowired
    MessageDAOImplementation messageDAO;

    @Override
    @Transactional
    public List<GroupDTO> getAllGroupsDTO() {
        return GroupConverter.getGroupDTOList(groupDAO.getAllGroups());
    }

    @Override
    public boolean saveGroup(GroupDTO groupDTO) {
        GroupEntity group = GroupConverter.getGroupEntity(groupDTO);
        if (group.getId() != 0) {
            List<UserEntity> users = new ArrayList<UserEntity>();
            for (int cur : groupDTO.getUserId()) {
                users.add(userDAO.getUser(cur));
            }
            group.setUserList(users);
        }
        try {
            groupDAO.saveGroup(group);
        } catch(org.hibernate.exception.ConstraintViolationException | org.springframework.dao.DataIntegrityViolationException e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public GroupEntity getGroup(int id) {
        return groupDAO.getGroup(id);
    }

    @Override
    @Transactional
    public void deleteGroup(int id) {
        GroupEntity group = getGroup(id);
        for (MessageEntity message : group.getMessages()) {
            deleteMessage(message.getId());
        }
        groupDAO.deleteGroup(id);
    }

    @Override
    @Transactional
    public List<UserDTO> getAllUsersDTO() {
        return UserConverter.getUserDTOList(userDAO.getAllUsers());
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        UserEntity user = UserConverter.getUserEntity(userDTO);
        if (user.getId() != 0) {
            List<GroupEntity> groups = new ArrayList<GroupEntity>();
            for (int cur : userDTO.getGroupId()) {
                groups.add(groupDAO.getGroup(cur));
            }
            user.setGroupList(groups);
        }
        try  {
            userDAO.saveUser(user);
        } catch (org.hibernate.exception.ConstraintViolationException | org.springframework.dao.DataIntegrityViolationException e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public UserEntity getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public List<UserDTO> getSpecificUsersDTO(String name) {
        String first = null;
        String second = null;
        for (int i = 0; i < name.length(); i++) {
            if ((int) name.charAt(i) == 32) {
                first = name.substring(0, i);
                second = name.substring(i + 1, name.length());
            }
        }
        return UserConverter.getUserDTOList(userDAO.getSpecificUsers(first, second));
    }

    @Override
    public List<GroupDTO> getSpecificGroupsDTO(String groupName) {
        List<GroupEntity> groups = groupDAO.getSpecificGroupsDTO(groupName);
        List<GroupDTO> specificGroupsDTO = GroupConverter.getGroupDTOList(groups);
        return specificGroupsDTO;
    }

    @Override
    @Transactional
    public void deleteLink(int userId, int groupId) {
        UserEntity user = userDAO.getUser(userId);
        GroupEntity group = groupDAO.getGroup(groupId);
        userDAO.deleteLink(user, group);
    }

    @Override
    @Transactional
    public List<UserDTO> getNonMembersDTO(int id) {
        GroupEntity group = groupDAO.getGroup(id);
        List<String> userList = group.getUserEmails();
        return UserConverter.getUserDTOList(userDAO.getNonMembers(userList));
    }

    @Override
    @Transactional
    public MessageEntity getMessage(int id) {
        return messageDAO.getMessage(id);
    }
    @Override
    @Transactional
    public MessageDTO getMessageDTO(int id) {
        return MessageConverter.getMessageDTO(messageDAO.getMessage(id));
    }

    @Override
    @Transactional
    public void createLink(int groupId, int userId) {
        GroupEntity group = groupDAO.getGroup(groupId);
        UserEntity user = userDAO.getUser(userId);
        groupDAO.createLink(group, user);
    }

    @Override
    public boolean persistUser(UserDTO userDTO) {
        UserEntity user = UserConverter.getUserEntity(userDTO);
        try {
            userDAO.persistUser(user);
        } catch (org.hibernate.exception.ConstraintViolationException | org.springframework.dao.DataIntegrityViolationException e) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void saveMessage(MessageDTO messageDTO) {
        GroupEntity group = groupDAO.getGroup(messageDTO.getGroupId());
        MessageEntity message = MessageConverter.getMessageEntity(messageDTO, group);
        messageDAO.saveMessage(message);
    }

    @Override
    @Transactional
    public List<MessageDTO> getAllMessagesDTO() {
        return MessageConverter.getAllMessagesDTO(messageDAO.getAllMessages());
    }

    @Override
    @Transactional
    public void deleteMessage(int id) {
        messageDAO.deleteMessage(id);
    }

    @Override
    @Transactional
    public UserDTO getUserDTO(int id) {
        UserEntity user = userDAO.getUser(id);
        UserDTO userDTO = UserConverter.getUserDTO(user);
        userDTO.setGroupId(user.getGroupId());
        return userDTO;
    }

    @Override
    @Transactional
    public GroupDTO getGroupDTO(int id) {
        GroupEntity group = groupDAO.getGroup(id);
        GroupDTO groupDTO = GroupConverter.getGroupDTO(group);
        return groupDTO;
    }

}

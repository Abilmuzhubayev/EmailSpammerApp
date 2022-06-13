package com.Abilmansur.EmailSpammer.Converters;

import com.Abilmansur.EmailSpammer.DTO.GroupDTO;
import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.DTO.UserDTO;
import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class GroupConverter {
    public static GroupEntity getGroupEntity(GroupDTO groupDTO) {
        GroupEntity group = new GroupEntity();
        group.setId(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        return group;
    }

    public static GroupDTO getGroupDTO(GroupEntity group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setName(group.getName());
        groupDTO.setUserId(group.getUserId());
        return groupDTO;
    }

    public static List<GroupDTO> getGroupDTOList(List<GroupEntity> groups) {
        return groups.stream().map(x -> getGroupDTO(x)).collect(Collectors.toList());
    }
}

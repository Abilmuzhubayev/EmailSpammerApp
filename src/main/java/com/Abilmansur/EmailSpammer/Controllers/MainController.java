package com.Abilmansur.EmailSpammer.Controllers;

import com.Abilmansur.EmailSpammer.DTO.GroupDTO;
import com.Abilmansur.EmailSpammer.DTO.MessageDTO;
import com.Abilmansur.EmailSpammer.DTO.UserDTO;
import com.Abilmansur.EmailSpammer.Service.EmailService.EmailSenderService;
import com.Abilmansur.EmailSpammer.Entity.GroupEntity;
import com.Abilmansur.EmailSpammer.Entity.MessageEntity;
import com.Abilmansur.EmailSpammer.Entity.UserEntity;
import com.Abilmansur.EmailSpammer.Service.AppService.AppServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AppServiceImplementation service;
    @Autowired
    EmailSenderService emailSenderService;

    //showing every user in the system
    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        if (!model.containsAttribute("allUsers")) {
            List<UserDTO> allUsers = service.getAllUsersDTO();
            model.addAttribute("allUsers", allUsers);
        }
        return "all-users";
    }

    //showing every group in the system
    @GetMapping("/allGroups")
    public String allGroups(Model model) {
        if (!model.containsAttribute("allGroups")) {
            List<GroupDTO> allGroups = service.getAllGroupsDTO();
            model.addAttribute("allGroups", allGroups);
        }
        return "all-groups";
    }

    //returns view for user addition form
    @GetMapping("/addUser")
    public String addUserForm(Model model){
        model.addAttribute("user", new UserDTO());
        return "add-user-form";
    }

    //adds new user from model to the DB
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
        if (service.saveUser(userDTO) == false) {
            String emailExists = "The email is already in use";
            model.addAttribute("emailExists", emailExists);
            if (userDTO.getId() == 0) {
                return "add-user-form";
            } else {
                return "edit-user-form";
            }
        }
        return "redirect:/allUsers";
    }

    //returns edit form for existing user
    @GetMapping("/editUser/{id}")
    public String updateUserForm(@PathVariable int id, Model model) {
        UserDTO userDTO = service.getUserDTO(id);
        model.addAttribute("user", userDTO);
        return "edit-user-form";
    }


    //returns group adding form
    @GetMapping("/addGroup")
    public String addGroupForm(Model model){
        model.addAttribute("group", new GroupDTO());
        return "add-group-form";
    }

    //saves group from the model to the DB
    @PostMapping("/addGroup")
    public String addGroup(@ModelAttribute("group") GroupDTO groupDTO, Model model) {
        if (service.saveGroup(groupDTO) == false) {
            String groupNameExists = "The group name is already in use";
            model.addAttribute("group", groupDTO);
            model.addAttribute("groupNameExists", groupNameExists);
            if (groupDTO.getId() == 0) {
                return "add-group-form";
            } else {
                return "edit-group-form";
            }
        }
        return "redirect:/allGroups";
    }

    //delete user from the db
    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        service.deleteUser(id);
        return "redirect:/allUsers";
    }

    //delete group from the db
    @RequestMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable int id) {
        service.deleteGroup(id);
        return "redirect:/allGroups";
    }

    //showing group details
    @GetMapping("/groupDetails/{id}")
    public String groupDetails(@PathVariable int id, Model model) {
        GroupEntity group = service.getGroup(id);
        model.addAttribute("group", group);
        return "group-details";
    }

    //deleting an user from group
    @GetMapping("/deleteFromGroup/{groupId}/{userId}")
    public String deleteUserFromGroup(@PathVariable("groupId") int groupId, @PathVariable("userId") int userId, Model model) {
        service.deleteLink(userId, groupId);
        return "redirect:/groupDetails/{groupId}";
    }

    //filtering by name and surname
    @GetMapping("/searchUsers/")
    public String searchUsers(@RequestParam("name") String name,  Model model) {
        List<UserDTO> allUsers = service.getSpecificUsersDTO(name);
        model.addAttribute("allUsers", allUsers);
        return allUsers(model);
    }
    @GetMapping("/searchGroups/")
    public String searchGroups(@RequestParam("groupName") String groupName, Model model) {
        List<GroupDTO> allGroups = service.getSpecificGroupsDTO(groupName);
        model.addAttribute("allGroups", allGroups);
        return allGroups(model);
    }

    //showing users that are not members of the group
    @GetMapping("/addToGroup/{groupId}")
    public String addUsersToGroup(Model model, @PathVariable("groupId") int groupId) {
        List<UserDTO> nonMembers = service.getNonMembersDTO(groupId);
        model.addAttribute("nonMembers", nonMembers);
        model.addAttribute("groupId", groupId);
        return "add-users-to-groups-form";
    }

    //adding a user to a group
    @RequestMapping("/addUserToGroup/{groupId}/{userId}")
    public String addUserToGroup(Model model, @PathVariable("groupId") int groupId, @PathVariable("userId") int userId) {
        service.createLink(groupId, userId);
        return "redirect:/addToGroup/{groupId}";
    }

    //returns a view for email composition
    @GetMapping("/sendMessage")
    public String composeEmail(Model model) {
        List<GroupDTO> allGroups = service.getAllGroupsDTO();
        model.addAttribute("allGroups", allGroups);
        model.addAttribute("message", new MessageDTO());
        return "compose-email";
    }

    //gets message object from the model and sends a message
    @PostMapping("/sendMessage")
    public String sendMessage(@ModelAttribute("message") MessageDTO messageDTO, Model model) {
        List<GroupDTO> allGroups = service.getAllGroupsDTO();
        model.addAttribute("allGroups", allGroups);
        switch (emailSenderService.sendEmail(messageDTO)) {
            case "No recipients":
                String noRecipients = "The group you have selected has no users. Select another group or add users to the current one.";
                model.addAttribute("noRecipients", noRecipients);
                return "compose-email";

            case "No connection":
                String poorConnection = "Something went wrong. Check your connection.";
                model.addAttribute("poorConnection", poorConnection);
                return "compose-email";
        }
        return "redirect:/emailRecords";
    }

    //shows mailing history
    @GetMapping("/emailRecords")
    public String showEmailRecords(Model model) {
        List<MessageDTO> allMessages = service.getAllMessagesDTO();
        model.addAttribute("allMessages", allMessages);
        return "email-records";
    }

    //deleting a message
    @RequestMapping ("/deleteMessage/{id}")
    public String deleteMessage(@PathVariable int id) {
        service.deleteMessage(id);
        return "redirect:/emailRecords";
    }

    //getting text of a message
    @GetMapping("/messageDetails/{id}")
    public String showMessageDetails(@PathVariable int id, Model model) {
        MessageDTO messageDTO = service.getMessageDTO(id);
        model.addAttribute("message", messageDTO);
        return "message-details";
    }

    //resend form of the email
    @GetMapping("/resendMessage/{id}")
    public String resendMessage(@PathVariable int id, Model model) {
        List<GroupDTO> allGroups = service.getAllGroupsDTO();
        model.addAttribute("allGroups", allGroups);
        MessageDTO messageDTO = service.getMessageDTO(id);
        model.addAttribute("message", messageDTO);
        return "compose-email";
    }

    //returns a form for group update
    @GetMapping("/updateGroup/{id}")
    public String updateGroup(@PathVariable int id, Model model) {
        GroupDTO groupDTO = service.getGroupDTO(id);
        model.addAttribute("group", groupDTO);
        return "edit-group-form";
    }
}

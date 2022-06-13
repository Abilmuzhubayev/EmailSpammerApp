package com.Abilmansur.EmailSpammer.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class GroupEntity {

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_description")
    private String description;

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @ManyToMany
    @JoinTable(
            name = "user_group"
            , joinColumns = @JoinColumn(name = "group_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> userList;

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST)
    private List<MessageEntity> messages;

    public List<String> getUserEmails() {
        List<String> userEmails = new ArrayList<String>();
        for (UserEntity u : userList) {
            userEmails.add(u.getEmail());
        }
        return userEmails;
    }

    public List<Integer> getUserId() {
        List<Integer> userId = new ArrayList<Integer>();
        for (UserEntity u : userList) {
            userId.add(u.getId());
        }
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public void addUser(UserEntity user) {
        userList.add(user);
    }

}

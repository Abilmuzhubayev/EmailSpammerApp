package com.Abilmansur.EmailSpammer.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String name;

    @Column(name = "lastname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @ManyToMany
    @JoinTable(
            name = "user_group"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<GroupEntity> groupList;

    public List<GroupEntity> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupEntity> groupList) {
        this.groupList = groupList;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity() {
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

    public UserEntity(String email, String name, String surname, int age, String gender) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    public List<Integer> getGroupId() {
        List<Integer> groupId = new ArrayList<Integer>();
        for (GroupEntity g : groupList) {
            groupId.add(g.getId());
        }
        return groupId;
    }


    public void removeGroup(GroupEntity group) {
        groupList.remove(group);
        group.getUserList().remove(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package com.Abilmansur.EmailSpammer.Entity;

import com.Abilmansur.EmailSpammer.DTO.MessageDTO;

import javax.persistence.*;


@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message_subject")
    private String subject;

    @Column(name = "message_body")
    private String body;

    @Column(name = "message_time")
    private String localTime;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;


    public MessageEntity() {
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

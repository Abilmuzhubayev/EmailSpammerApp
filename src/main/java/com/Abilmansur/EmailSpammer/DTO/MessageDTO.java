package com.Abilmansur.EmailSpammer.DTO;

public class MessageDTO {
    private int id;
    private int groupId;
    private String groupName;
    private String subject;
    private String body;
    private String localTime;

    public MessageDTO() {
    }
    public String getGroupName() {
        return groupName;
    }
    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public int getGroupId() {
        return groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

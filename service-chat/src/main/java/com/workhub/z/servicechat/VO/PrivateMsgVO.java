package com.workhub.z.servicechat.VO;

public class PrivateMsgVO {
    private String receiverId;

    private String senderId;

    private String type;

    private String levels;

    private String content;

    private String sendAvatar;

    private String recAvatar;

    private String sendTime;

    private String isRead;

    public void setReceiverId(String receiverId){
        this.receiverId = receiverId;
    }
    public String getReceiverId(){
        return this.receiverId;
    }
    public void setSenderId(String senderId){
        this.senderId = senderId;
    }
    public String getSenderId(){
        return this.senderId;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setLevels(String levels){
        this.levels = levels;
    }
    public String getLevels(){
        return this.levels;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setSendAvatar(String sendAvatar){
        this.sendAvatar = sendAvatar;
    }
    public String getSendAvatar(){
        return this.sendAvatar;
    }
    public void setRecAvatar(String recAvatar){
        this.recAvatar = recAvatar;
    }
    public String getRecAvatar(){
        return this.recAvatar;
    }
    public void setSendTime(String sendTime){
        this.sendTime = sendTime;
    }
    public String getSendTime(){
        return this.sendTime;
    }
    public void setIsRead(String isRead){
        this.isRead = isRead;
    }
    public String getIsRead(){
        return this.isRead;
    }
}

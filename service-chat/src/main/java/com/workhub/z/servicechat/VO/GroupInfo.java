package com.workhub.z.servicechat.VO;
/**
*@Description: 前端接口，群组信息
*@Author: 忠
*@date: 2019/5/14
*/
public class GroupInfo
{
    private String id;

    private String name;

    private String avatar;

    private String description;

    private String subject;

    private String securityClass;

    private String createTime;

    private String creator;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public String getSubject(){
        return this.subject;
    }
    public void setSecurityClass(String securityClass){
        this.securityClass = securityClass;
    }
    public String getSecurityClass(){
        return this.securityClass;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setCreator(String creator){
        this.creator = creator;
    }
    public String getCreator(){
        return this.creator;
    }
}

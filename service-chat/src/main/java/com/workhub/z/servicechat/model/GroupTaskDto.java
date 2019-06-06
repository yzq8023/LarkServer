package com.workhub.z.servicechat.model;

import com.workhub.z.servicechat.entity.ZzGroup;

import java.util.Date;
import java.util.List;

public class GroupTaskDto {
    //群组标识
    private String groupId;

    //时间戳
    private Date timestamp;

    //修改人
    private String reviser;

    //操作类型，1，加入群组；2，退出群组；3.关闭群组；4.删除群组
    private int type;

    // 用户列表
    private List<UserListDto> userList;

    //创建群组po
    private ZzGroup zzGroup;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getReviser() {
        return reviser;
    }

    public void setReviser(String reviser) {
        this.reviser = reviser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<UserListDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListDto> userList) {
        this.userList = userList;
    }

    public ZzGroup getZzGroup() {
        return zzGroup;
    }

    public void setZzGroup(ZzGroup zzGroup) {
        this.zzGroup = zzGroup;
    }
}

package com.workhub.z.servicechat.VO;

/**
*@Description: 人员信息，前端交互实体类
*@Author: 忠
*@date: 2019/5/15
*/
public class UserInfoVO {

    private String userId;
    private int userLevels;
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserLevels(int userLevels) {
        this.userLevels = userLevels;
    }
    public int getUserLevels() {
        return userLevels;
    }

}
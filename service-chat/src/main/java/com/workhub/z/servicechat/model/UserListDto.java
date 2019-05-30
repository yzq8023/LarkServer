package com.workhub.z.servicechat.model;

public class UserListDto {

    private String userId;//人id

    private String userLevels;//人等级

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLevels() {
        return userLevels;
    }

    public void setUserLevels(String userLevels) {
        this.userLevels = userLevels;
    }
}

package com.workhub.z.servicechat.VO;

public class UserInfoVO {
    //用户id
    private String id;
    //用户名称
    private String name;
     //用户头像路径
    private String avartar;
    //是否在线1在线0离线
    private String online;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}

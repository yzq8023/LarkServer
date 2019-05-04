package com.github.hollykunge.security.admin.vo;

/**
 * 业务对象：前端获取用户信息
 * @author 协同设计小组
 * @date 2017/8/22
 */
public class FrontUser {
    //系统内id
    private String id;
    //身份证号
    private String username;
    //用户姓名
    private String name;
    //自述信息
    private String description;
    //头像
    private String avatar;
    //状态0离职1在职
    private Integer status;
    //电话
    private Integer telephone;
    //系统内删除状态0未删除
    private Integer deleted;
    //角色
    private String roleId;

    private UserRole userRole;



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

}

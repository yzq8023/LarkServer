package com.github.hollykunge.security.admin.vo;

import com.github.hollykunge.security.api.vo.authority.PermissionInfo;

import java.util.List;

/**
 * 业务对象：用户角色权限，负责前端菜单展示使用
 *
 * @author: holly
 * @since: 2019/4/29
 */
public class UserRole {
    //角色id
    private String id;
    //角色名称
    private String name;
    //角色描述
    private String describe;
    //角色状态
    private Integer status;
    //创建人id
    private String creatorId;
    //创建时间
    private Long createTime;
    //删除状态0未删除
    private Integer deleted;

    private List<PermissionInfo> permissionInfos;

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public List<PermissionInfo> getPermissionInfos() {
        return permissionInfos;
    }

    public void setPermissionInfos(List<PermissionInfo> permissionInfos) {
        this.permissionInfos = permissionInfos;
    }
}

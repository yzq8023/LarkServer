package com.github.hollykunge.security.admin.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ResourceRoleMap extends BaseEntity {

    @Column(name = "RESOURCE_ID")
    private String resourceId;

    @Column(name = "RESOURCE_TYPE")
    private String resourceType;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "DEFAULT_CHECKED")
    private Integer defaultChecked;

}
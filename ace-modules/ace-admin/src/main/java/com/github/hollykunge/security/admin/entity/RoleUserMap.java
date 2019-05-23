package com.github.hollykunge.security.admin.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class RoleUserMap extends BaseEntity {

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "USER_ID")
    private String userId;
}
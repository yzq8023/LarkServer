package com.github.hollykunge.security.admin.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class OrgUserMap extends BaseEntity {

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "USER_ID")
    private String userId;

}
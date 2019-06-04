package com.github.hollykunge.security.admin.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ADMIN_NOTICE")
public class Notice extends BaseEntity {
    @Column(name = "TITLE")
    private String title;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ATTACHMENT")
    private String attachment;

    @Column(name = "IS_TOP")
    private String isTop;

    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "ORG_NAME")
    private String orgName;
}
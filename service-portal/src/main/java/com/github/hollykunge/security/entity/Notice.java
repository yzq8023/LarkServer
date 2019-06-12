package com.github.hollykunge.security.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 工作台-公告栏
 * @author: dd
 * @since: 2019-06-07
 */
@Data
@Table(name = "PORTAL_NOTICE")
public class Notice {
    @Column(name = "ID")
    @Id
    private String id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "TOP")
    private String top;
    @Column(name = "ORG_ID")
    private String orgId;
}

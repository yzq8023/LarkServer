package com.github.hollykunge.security.admin.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ADMIN_MENU")
public class Menu extends BaseEntity {

    @Column(name = "CODE")
    private String code;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "HREF")
    private String href;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ORDER_NUM")
    private Long orderNum;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PATH")
    private String path;

    @Column(name = "ENABLED")
    private String enabled;
}
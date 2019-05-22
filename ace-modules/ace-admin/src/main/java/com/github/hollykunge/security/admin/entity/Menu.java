package com.github.hollykunge.security.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Menu {

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
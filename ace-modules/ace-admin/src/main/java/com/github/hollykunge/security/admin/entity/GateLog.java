package com.github.hollykunge.security.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Table(name = "ADMIN_GATELOG")
public class GateLog {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MENU")
    private String menu;

    @Column(name = "OPT")
    private String opt;

    @Column(name = "URI")
    private String uri;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER")
    private String crtUser;

    @Column(name = "CRT_NAME")
    private String crtName;

    @Column(name = "CRT_HOST")
    private String crtHost;

    @Column(name = "IS_SUCCESS")
    private String isSuccess;

    @Column(name = "P_ID")
    private String pid;

}
package com.github.hollykunge.security.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @description: 卡片设置- 用户卡片表
 * @author zhhongyu
 * @since 2019年06月1日
 */
@Data
@Table(name = "PORTAL_USER_CARD")
public class UserCard {

    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID ")
    private String userId;

    @Column(name = "CARD_ID")
    private String cardId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "X")
    private Integer x;

    @Column(name = "Y")
    private Integer y;

    @Column(name = "W")
    private Integer w;

    @Column(name = "H")
    private Integer h;

    @Column(name = "I")
    private String i;
}

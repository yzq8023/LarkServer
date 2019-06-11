package com.github.hollykunge.security.entity;

import lombok.Data;
import org.nutz.dao.entity.annotation.Table;

import javax.persistence.Column;

/**
 * @description: 卡片设置- 用户卡片表
 * @author zhhongyu
 * @since 2019年06月1日
 */
@Data
@Table("PORTAL_USER_CARD")
public class UserCard {

    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID ")
    private String userId;

    @Column(name = "CARD_ID")
    private String cardId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "w")
    private Integer w;

    @Column(name = "h")
    private Integer h;

    @Column(name = "i")
    private String i;
}

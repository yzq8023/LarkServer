package com.github.hollykunge.security.vo;

import lombok.Data;

/**
 * 用户卡片实体类，供前端展示
 * @author zhhongyu
 * @since 2019-06-11
 */
@Data
public class UserCardVO {
    private String id;

    private String title;

    private String url;

    private Integer x;

    private Integer y;

    private Integer w;

    private Integer h;

    private String i;
}

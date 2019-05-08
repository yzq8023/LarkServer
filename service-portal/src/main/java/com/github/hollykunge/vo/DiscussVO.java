package com.github.hollykunge.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 研讨页面展示实体类
 */
@Data
@Getter
@Setter
public class DiscussVO {

    private String id;

    private String user;

    private String avatar;

    private String content;

    private Integer unread;

    private String time;
}

package com.github.hollykunge.security.entity;

import lombok.Data;

/**
 * @ClassName Like
 * @Description 问题或评论点赞
 * @Author hollykunge
 * @Date 2019/6/30 13:17
 * @Version 1.0
 **/
@Data
public class Like {

    private Integer id;
    private String likeId;
    private String userId;
    /**
     * 点赞实体id
     */
    private String entityId;
}

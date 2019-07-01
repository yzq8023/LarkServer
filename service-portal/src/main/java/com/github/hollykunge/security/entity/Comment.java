package com.github.hollykunge.security.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

/**
 * @ClassName Comment
 * @Description 评论
 * @Author hollykunge
 * @Date 2019/6/29 23:26
 * @Version 1.0
 **/
@Data
public class Comment extends BaseEntity {
    /**
     * 属于哪个问题
     */
    private String feedBackId;
    private String content;
}

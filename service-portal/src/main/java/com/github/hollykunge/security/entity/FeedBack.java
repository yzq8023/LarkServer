package com.github.hollykunge.security.entity;

import com.github.hollykunge.security.common.entity.BaseEntity;
import lombok.Data;

/**
 *@ClassName FeedBack
 *@Description 问题反馈
 *@Author hollykunge
 *@Date
 *@Version 1.0
 **/
@Data
public class FeedBack extends BaseEntity {
    private String title;
    private String content;
    /**
     * 问题状态，true打开，false关闭
     */
    private Boolean status;
    /**
     * 问题类型
     */
    private String type;
    /**
     * 普通用户可见性
     */
    private Boolean visible;
}

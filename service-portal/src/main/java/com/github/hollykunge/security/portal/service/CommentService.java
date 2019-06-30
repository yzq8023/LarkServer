package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.Comment;
import com.github.hollykunge.security.mapper.CommentMapper;

/**
 * @ClassName CommentService
 * @Description TODO
 * @Author hollykunge
 * @Date 2019/6/30 13:28
 * @Version 1.0
 **/
public class CommentService extends BaseBiz<CommentMapper, Comment> {
    @Override
    protected String getPageName() {
        return null;
    }
}

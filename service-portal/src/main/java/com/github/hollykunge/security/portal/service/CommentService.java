package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.entity.Comment;
import com.github.hollykunge.security.mapper.CommentMapper;
import org.apache.commons.lang.StringUtils;

import java.util.List;

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

    public List<Comment> feedBackComments(String feedBackId){
        if(StringUtils.isEmpty(feedBackId)){
            throw new BaseException("问题反馈的id不能为空...");
        }
        Comment comment = new Comment();
        comment.setFeedBackId(feedBackId);
        return mapper.select(comment);
    }
}

package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.Comment;
import com.github.hollykunge.security.portal.service.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CommentController
 * @Description 评论
 * @Author hollykunge
 * @Date 2019/6/30 13:27
 * @Version 1.0
 **/
@RestController
@RequestMapping("comment")
public class CommentController extends BaseController<CommentService, Comment> {
    /**
     * 根据问题反馈id获取对应的评论列表
     * @param feedBackId 问题反馈id
     * @return
     */
    @RequestMapping("feedBackComments")
    @ResponseBody
    public ListRestResponse<List<Comment>> feedBackComments(@RequestParam String feedBackId){
        List<Comment> comments = baseBiz.feedBackComments(feedBackId);
        return new ListRestResponse<List<Comment>>("",comments.size(),comments);
    }
}

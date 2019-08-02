package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.util.Query;
import com.github.hollykunge.security.entity.Feedback;
import com.github.hollykunge.security.portal.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 问题反馈
 * @author hollykunge
 */
@RestController
@RequestMapping("feedback")
public class FeedbackController extends BaseController<FeedbackService, Feedback> {

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public TableResultResponse<Feedback> page(@RequestParam Map<String, Object> params) {
        String userId = request.getHeader("userId");
        params.put("crtUser",userId);
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }
}

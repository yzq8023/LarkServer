package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.admin.biz.NoticeBiz;

import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.entity.Notice;
import com.github.hollykunge.security.admin.rpc.service.PermissionService;
import com.github.hollykunge.security.admin.vo.FrontUser;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * @Author: yzq
 * @Date: 创建于 2019/6/4 16:37
 */

@Controller
@RequestMapping("notice")
@Api("公告模块")
public class NoticeController extends BaseController<NoticeBiz,Notice> {
    @Override
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Notice> page(@RequestParam Map<String, Object> params){
        String userId = getCurrentUserId();
        String orgId = baseBiz.getOrgIdByUserId(userId);
        if (!params.containsKey("orgId")) {
            params.put("orgId", orgId);
        }
        return super.page(params);
    }
}

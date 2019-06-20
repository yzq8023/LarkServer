package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.UserCard;
import com.github.hollykunge.security.entity.UserCommonTools;
import com.github.hollykunge.security.portal.service.UserCommonToolsService;
import com.github.hollykunge.security.vo.UserCardVO;
import com.github.hollykunge.security.vo.UserCommonToolsVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户设置常用工具类接口
 * @author zhhongyu
 * @since 2019-06-19
 */
@RestController
@RequestMapping("userCommonTools")
public class UserCommonToolsController extends BaseController<UserCommonToolsService, UserCommonTools> {
    /**
     * 获取所有的常用工具列表，如果该用户设置该常用工具为显示，
     * defaultchecke字段为true，否则为false
     * @return
     */
    @RequestMapping(value = "/allTools", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<UserCommonToolsVO>> allCommonTools() {
        String userID =  request.getHeader("userId");
        if(StringUtils.isEmpty(userID)){
            throw new BaseException("request contains no user...");
        }
        List<UserCommonToolsVO> userCommonToolsVOS = baseBiz.allCommonTools(userID);
        return new ListRestResponse<>("",userCommonToolsVOS.size(),userCommonToolsVOS);
    }

    /**
     * 用户增加常用工具
     * @param userCommonTools 用户常用工具实体类，toolId必传
     * @return
     */
    @Override
    @RequestMapping(value = "/myself", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<UserCommonTools> add(@RequestBody UserCommonTools userCommonTools) {
        if(StringUtils.isEmpty(userCommonTools.getToolId())){
            throw new BaseException("commonToolId is null..");
        }
        String userID = request.getHeader("userId");
        if(StringUtils.isEmpty(userID)){
            throw new BaseException("request contains no user...");
        }
        userCommonTools.setUserId(userID);
        if(baseBiz.selectCount(userCommonTools) > 0){
            return new ObjectRestResponse<>().rel(false).msg("fail，user exist this commonTool...");
        }
        return super.add(userCommonTools).rel(true).msg("success...").rel(true);
    }

    /**
     * 用户移除常用工具类
     * @param commonToolId
     * @return
     */
    @RequestMapping(value = "/myself", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Boolean> removeCommontools(@RequestParam String commonToolId) {
        if(StringUtils.isEmpty(commonToolId)){
            throw new BaseException("commonToolId is null");
        }
        String userID =  request.getHeader("userId");
        if(StringUtils.isEmpty(userID)){
            throw new BaseException("request contains no user...");
        }
        UserCommonTools userCommonTools= new UserCommonTools();
        userCommonTools.setUserId(userID);
        userCommonTools.setToolId(commonToolId);
        if(baseBiz.selectCount(userCommonTools) == 0){
            return new ObjectRestResponse<>().msg("fail，user don't have this commonTool......");
        }
        baseBiz.delete(userCommonTools);
        return new ObjectRestResponse<>().msg("success..").rel(true);
    }

    /**
     * 用户获取显示常用工具类列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/myself", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<UserCommonToolsVO>> userCards(HttpServletRequest request) {
        String userID =  request.getHeader("userId");
        if(StringUtils.isEmpty(userID)){
            throw new BaseException("request contains no user...");
        }
        List<UserCommonToolsVO> userCardVOS = baseBiz.userCommonTools(userID);
        return new ListRestResponse<>("",userCardVOS.size(),userCardVOS);
    }
}

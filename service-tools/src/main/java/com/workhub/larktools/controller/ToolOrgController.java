package com.workhub.larktools.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.workhub.larktools.service.ToolOrgService;
import com.workhub.larktools.tool.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Map;

/**
 * author:zhuqz
 * description:工具上传组织管理
 * date:2019/8/13 11:27
 **/
@RestController
@RequestMapping("/toolOrg")
public class ToolOrgController {
    private static Logger log = LoggerFactory.getLogger(ToolOrgController.class);
    @Resource
    private ToolOrgService toolOrgService;
    @Resource
    private HttpServletRequest httpServletRequest;
    /**
    * @MethodName: add
     * @Description: 增加组织节点
     * @Param: [params] 组织名称 ：name ，父节点pid,如果为空认为是根节点,order 排序
     * @Return: com.github.hollykunge.security.common.msg.ObjectRestResponse
     * @Author: admin
     * @Date: 2019/8/13
    **/
    @PostMapping("/add")
    public ObjectRestResponse add(@RequestParam Map params) throws Exception{
        ObjectRestResponse res = new ObjectRestResponse();
        res.msg("200");
        res.rel(true);
        String userId = CommonUtil.nulToEmptyString(httpServletRequest.getHeader("userId"));
        String userName = URLDecoder.decode(CommonUtil.nulToEmptyString(httpServletRequest.getHeader("userName")),"UTF-8");
        params.put("userId",userId);
        params.put("id", UUIDUtils.generateShortUuid());
        int i = toolOrgService.add(params);
        return res;
    }
}

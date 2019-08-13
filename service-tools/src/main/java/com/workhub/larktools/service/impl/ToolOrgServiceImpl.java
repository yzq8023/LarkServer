package com.workhub.larktools.service.impl;

import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.workhub.larktools.dao.ToolOrgDao;
import com.workhub.larktools.feign.IUserService;
import com.workhub.larktools.service.ToolOrgService;
import com.workhub.larktools.tool.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//import com.workhub.larktools.feign.IUserService;

/**
 * author:zhuqz
 * description:
 * date:2019/8/13 15:07
 **/
@Service("toolOrgService")
public class ToolOrgServiceImpl implements ToolOrgService {
    private static Logger log = LoggerFactory.getLogger(ToolOrgServiceImpl.class);
     @Resource
     private ToolOrgDao toolOrgDao;
    @Resource
    private IUserService iUserService;

    @Transactional
    public  int add(Map param){
        String pid = CommonUtil.nulToEmptyString(param.get("pid"));
        if("".equals(pid)){//根节点
            param.put("pid","-1");
            //调用feign接口获取用户组织信息
            String userId = param.get("userId").toString();
           // userId = "yanzhenqing";
            UserInfo userInfo = iUserService.info(userId);
            String orgCode = userInfo.getOrgCode();
            Map orgParam = new HashMap();
            orgParam.put("id", UUIDUtils.generateShortUuid());
            orgParam.put("userId",userId);
            orgParam.put("treeId",param.get("id"));
            orgParam.put("orgCode",orgCode);
            toolOrgDao.addTreeOrg(orgParam);
        }
        String order = CommonUtil.nulToEmptyString(param.get("order"));
        if("".equals(order)){
            param.put("order","0");
        }


        return this.toolOrgDao.add(param);
    }
}

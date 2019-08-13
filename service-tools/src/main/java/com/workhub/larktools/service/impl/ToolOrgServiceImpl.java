package com.workhub.larktools.service.impl;

import com.workhub.larktools.dao.ToolOrgDao;
import com.workhub.larktools.service.ToolOrgService;
import com.workhub.larktools.tool.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

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
    public  int add(Map param){
        String pid = CommonUtil.nulToEmptyString(param.get("pid"));
        if("".equals(pid)){
            param.put("pid","-1");
        }
        String order = CommonUtil.nulToEmptyString(param.get("order"));
        if("".equals(order)){
            param.put("order","0");
        }
        return this.toolOrgDao.add(param);
    }
}

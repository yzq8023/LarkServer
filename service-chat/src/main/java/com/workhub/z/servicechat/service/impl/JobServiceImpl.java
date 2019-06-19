package com.workhub.z.servicechat.service.impl;


import com.workhub.z.servicechat.dao.JobDao;
import com.workhub.z.servicechat.service.JobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务-历史消息处理
 *
 * @author zhuqz
 * @since 2019-06-12
 */
@Service("jobServiceImpl")
public class JobServiceImpl implements JobService {
    @Resource
    private JobDao jobDao;
    public String backupHisMsg() throws Exception{
        String res = null;
        try {
            Map<String,String> param = new HashMap<>();
            param.put("out","");
           this.jobDao.backupHisMsg(param);
            res = param.get("out");
        }catch (Exception e){
            e.printStackTrace();
            res="-1";
        }
        return  res;
    }
}

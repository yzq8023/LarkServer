package com.workhub.z.servicechat.dao;

import java.util.Map;

/**
 * 定时任务
 *
 * @author zhuqz
 * @since 2019-06-12
 */
public interface JobDao {

    /**
     * 定时任务-历史消息处理
     *
     */
    String backupHisMsg(Map param);

}
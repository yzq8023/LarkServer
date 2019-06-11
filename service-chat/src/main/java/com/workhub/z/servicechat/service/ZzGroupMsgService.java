package com.workhub.z.servicechat.service;

import com.workhub.z.servicechat.entity.ZzGroupMsg;
import java.util.List;

/**
 * 群组消息表(ZzGroupMsg)表服务接口
 *
 * @author 忠
 * @since 2019-05-10 11:38:02
 */
public interface ZzGroupMsgService {

    /**
     * 通过ID查询单条数据
     *
     * @param msgId 主键
     * @return 实例对象
     */
    ZzGroupMsg queryById(String msgId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ZzGroupMsg> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param zzGroupMsg 实例对象
     * @return 实例对象
     */
    void insert(ZzGroupMsg zzGroupMsg);

    /**
     * 修改数据
     *
     * @param zzGroupMsg 实例对象
     * @return 实例对象
     */
    void update(ZzGroupMsg zzGroupMsg);

    /**
     * 通过主键删除数据
     *
     * @param msgId 主键
     * @return 是否成功
     */
    void deleteById(String msgId);

}
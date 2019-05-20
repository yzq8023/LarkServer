package com.workhub.z.servicechat.dao;

import com.workhub.z.servicechat.entity.ZzGroupMsg;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 群组消息表(ZzGroupMsg)表数据库访问层
 *
 * @author 忠
 * @since 2019-05-10 11:38:02
 */
public interface ZzGroupMsgDao {

    /**
     * 通过ID查询单条数据
     *
     * @param msgId 主键
     * @return 实例对象
     */
    ZzGroupMsg queryById(String msgId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ZzGroupMsg> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param zzGroupMsg 实例对象
     * @return 对象列表
     */
    List<ZzGroupMsg> queryAll(ZzGroupMsg zzGroupMsg);

    /**
     * 新增数据
     *
     * @param zzGroupMsg 实例对象
     * @return 影响行数
     */
    int insert(ZzGroupMsg zzGroupMsg);

    /**
     * 修改数据
     *
     * @param zzGroupMsg 实例对象
     * @return 影响行数
     */
    int update(ZzGroupMsg zzGroupMsg);

    /**
     * 通过主键删除数据
     *
     * @param msgId 主键
     * @return 影响行数
     */
    int deleteById(String msgId);

}
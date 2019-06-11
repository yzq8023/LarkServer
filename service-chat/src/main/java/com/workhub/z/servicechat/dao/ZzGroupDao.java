package com.workhub.z.servicechat.dao;

import com.workhub.z.servicechat.VO.GroupUserListVo;
import com.workhub.z.servicechat.entity.ZzGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 群组表(ZzGroup)表数据库访问层
 *
 * @author 忠
 * @since 2019-05-10 14:29:32
 */
public interface ZzGroupDao extends Mapper<ZzGroup> {

    /**
     * 通过ID查询单条数据
     *
     * @param groupId 主键
     * @return 实例对象
     */
    ZzGroup queryById(String groupId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ZzGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param zzGroup 实例对象
     * @return 对象列表
     */
    List<ZzGroup> queryAll(ZzGroup zzGroup);

    /**
     * 新增数据
     *
     * @param zzGroup 实例对象
     * @return 影响行数
     */
    int insert(ZzGroup zzGroup);

    /**
     * 修改数据
     *
     * @param zzGroup 实例对象
     * @return 影响行数
     */
    int update(ZzGroup zzGroup);

    /**
     * 通过主键删除数据
     *
     * @param groupId 主键
     * @return 影响行数
     */
    int deleteById(String groupId);

    List<GroupUserListVo> groupUserList(@Param("id")String id,@Param("start")Integer start,@Param("end")Integer end);

    Long groupUserListTotal(@Param("id")String id);

    List<ZzGroup> queryGroupListByUserId(@Param("id") String id);

    /**
     * 逻辑删除群
     * @param groupId 群id
     * @return  受影响行数
     * @author zhuqz
     * @since 2019-06-11
     */
    int deleteGroupLogic(@Param("groupId") String groupId, @Param("delFlg") String delFlg);
}
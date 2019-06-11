package com.workhub.z.servicechat.service;

import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupUserListVo;
import com.workhub.z.servicechat.entity.ZzGroup;

import java.util.List;

/**
 * 群组表(ZzGroup)表服务接口
 *
 * @author 忠
 * @since 2019-05-10 14:29:32
 */
public interface ZzGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param groupId 主键
     * @return 实例对象
     */
    ZzGroup queryById(String groupId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ZzGroup> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param zzGroup 实例对象
     * @return 实例对象
     */
    void insert(ZzGroup zzGroup);

    /**
     * 修改数据
     *
     * @param zzGroup 实例对象
     * @return 实例对象
     */
    Integer update(ZzGroup zzGroup);

    /**
     * 通过主键删除数据
     *
     * @param groupId 主键
     * @return 是否成功
     */
    boolean deleteById(String groupId);

    //获取群组成员信息
    PageInfo<GroupUserListVo> groupUserList(String id, int page, int size) throws Exception;
    //总记录数
    Long groupUserListTotal(String id) throws Exception;

    /**
    *@Description: 根据用户id查询用户所在群组
    *@Param: userId
    *@return: list<zzGroup>
    *@Author: 忠
    *@date: 2019/5/29
    */
    List<ZzGroup> queryGroupListByUserId(String id) throws Exception;

    /**
     * 逻辑删除群
     * @param groupId 群id
     * @return  1成功；-1错误
     * @author zhuqz
     * @since 2019-06-11
     */
    String deleteGroupLogic(String groupId, String delFlg) throws Exception;
}
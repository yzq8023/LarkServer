package com.workhub.z.servicechat.service.impl;

import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.dao.ZzGroupDao;
import com.workhub.z.servicechat.service.ZzGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 群组表(ZzGroup)表服务实现类
 *
 * @author 忠
 * @since 2019-05-10 14:29:32
 */
@Service("zzGroupService")
public class ZzGroupServiceImpl implements ZzGroupService {
    @Resource
    private ZzGroupDao zzGroupDao;

    /**
     * 通过ID查询单条数据
     *
     * @param groupId 主键
     * @return 实例对象
     */
    @Override
    public ZzGroup queryById(String groupId) {
        return this.zzGroupDao.queryById(groupId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzGroup> queryAllByLimit(int offset, int limit) {
        return this.zzGroupDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzGroup 实例对象
     * @return 实例对象
     */
    @Override
    public ZzGroup insert(ZzGroup zzGroup) {
        this.zzGroupDao.insert(zzGroup);
        return zzGroup;
    }

    /**
     * 修改数据
     *
     * @param zzGroup 实例对象
     * @return 实例对象
     */
    @Override
    public ZzGroup update(ZzGroup zzGroup) {
        this.zzGroupDao.update(zzGroup);
        return this.queryById(zzGroup.getGroupId());
    }

    /**
     * 通过主键删除数据
     *
     * @param groupId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String groupId) {
        return this.zzGroupDao.deleteById(groupId) > 0;
    }
}
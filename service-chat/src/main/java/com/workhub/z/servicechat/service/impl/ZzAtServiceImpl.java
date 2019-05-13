package com.workhub.z.servicechat.service.impl;

import com.workhub.z.servicechat.entity.ZzAt;
import com.workhub.z.servicechat.dao.ZzAtDao;
import com.workhub.z.servicechat.service.ZzAtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提及（@）功能实现(ZzAt)表服务实现类
 *
 * @author 忠
 * @since 2019-05-10 14:22:34
 */
@Service("zzAtService")
public class ZzAtServiceImpl implements ZzAtService {
    @Resource
    private ZzAtDao zzAtDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ZzAt queryById(String id) {
        return this.zzAtDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzAt> queryAllByLimit(int offset, int limit) {
        return this.zzAtDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzAt 实例对象
     * @return 实例对象
     */
    @Override
    public ZzAt insert(ZzAt zzAt) {
        this.zzAtDao.insert(zzAt);
        return zzAt;
    }

    /**
     * 修改数据
     *
     * @param zzAt 实例对象
     * @return 实例对象
     */
    @Override
    public ZzAt update(ZzAt zzAt) {
        this.zzAtDao.update(zzAt);
        return this.queryById(zzAt.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.zzAtDao.deleteById(id) > 0;
    }
}
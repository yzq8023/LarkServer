package com.workhub.z.servicechat.service.impl;

import com.workhub.z.servicechat.entity.ZzMsgTabRelation;
import com.workhub.z.servicechat.dao.ZzMsgTabRelationDao;
import com.workhub.z.servicechat.service.ZzMsgTabRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标记消息关系表(ZzMsgTabRelation)表服务实现类
 *
 * @author makejava
 * @since 2019-05-23 16:12:40
 */
@Service("zzMsgTabRelationService")
public class ZzMsgTabRelationServiceImpl implements ZzMsgTabRelationService {
    @Resource
    private ZzMsgTabRelationDao zzMsgTabRelationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ZzMsgTabRelation queryById(String id) {
        return this.zzMsgTabRelationDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzMsgTabRelation> queryAllByLimit(int offset, int limit) {
        return this.zzMsgTabRelationDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzMsgTabRelation 实例对象
     * @return 实例对象
     */
    @Override
    public ZzMsgTabRelation insert(ZzMsgTabRelation zzMsgTabRelation) {
        this.zzMsgTabRelationDao.insert(zzMsgTabRelation);
        return zzMsgTabRelation;
    }

    /**
     * 修改数据
     *
     * @param zzMsgTabRelation 实例对象
     * @return 实例对象
     */
    @Override
    public ZzMsgTabRelation update(ZzMsgTabRelation zzMsgTabRelation) {
        this.zzMsgTabRelationDao.update(zzMsgTabRelation);
        return this.queryById(zzMsgTabRelation.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.zzMsgTabRelationDao.deleteById(id) > 0;
    }
}
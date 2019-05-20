package com.workhub.z.servicechat.service.impl;

import com.workhub.z.servicechat.entity.ZzPrivateMsg;
import com.workhub.z.servicechat.dao.ZzPrivateMsgDao;
import com.workhub.z.servicechat.service.ZzPrivateMsgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 私人消息(ZzPrivateMsg)表服务实现类
 *
 * @author 忠
 * @since 2019-05-13 10:57:46
 */
@Service("zzPrivateMsgService")
public class ZzPrivateMsgServiceImpl implements ZzPrivateMsgService {
    @Resource
    private ZzPrivateMsgDao zzPrivateMsgDao;

    /**
     * 通过ID查询单条数据
     *
     * @param msgId 主键
     * @return 实例对象
     */
    @Override
    public ZzPrivateMsg queryById(String msgId) {
        return this.zzPrivateMsgDao.queryById(msgId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzPrivateMsg> queryAllByLimit(int offset, int limit) {
        return this.zzPrivateMsgDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzPrivateMsg 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer insert(ZzPrivateMsg zzPrivateMsg) {
        int insert = this.zzPrivateMsgDao.insert(zzPrivateMsg);
        return insert;
    }

    /**
     * 修改数据
     *
     * @param zzPrivateMsg 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer update(ZzPrivateMsg zzPrivateMsg) {
        int update = this.zzPrivateMsgDao.update(zzPrivateMsg);
        return update;
    }

    /**
     * 通过主键删除数据
     *
     * @param msgId 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(String msgId) {
        return this.zzPrivateMsgDao.deleteById(msgId) > 0;
    }
}
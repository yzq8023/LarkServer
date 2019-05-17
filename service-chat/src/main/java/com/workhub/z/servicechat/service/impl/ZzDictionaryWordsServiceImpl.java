package com.workhub.z.servicechat.service.impl;

import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.dao.ZzDictionaryWordsDao;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典词汇表(ZzDictionaryWords)表服务实现类
 *
 * @author makejava
 * @since 2019-05-17 14:56:57
 */
@Service("zzDictionaryWordsService")
public class ZzDictionaryWordsServiceImpl implements ZzDictionaryWordsService {
    @Resource
    private ZzDictionaryWordsDao zzDictionaryWordsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ZzDictionaryWords queryById(String id) {
        return this.zzDictionaryWordsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzDictionaryWords> queryAllByLimit(int offset, int limit) {
        return this.zzDictionaryWordsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象
     */
    @Override
    public ZzDictionaryWords insert(ZzDictionaryWords zzDictionaryWords) {
        this.zzDictionaryWordsDao.insert(zzDictionaryWords);
        return zzDictionaryWords;
    }

    /**
     * 修改数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象
     */
    @Override
    public ZzDictionaryWords update(ZzDictionaryWords zzDictionaryWords) {
        this.zzDictionaryWordsDao.update(zzDictionaryWords);
        return this.queryById(zzDictionaryWords.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.zzDictionaryWordsDao.deleteById(id) > 0;
    }
}
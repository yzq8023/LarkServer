package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.dao.ZzDictionaryWordsDao;
import com.workhub.z.servicechat.entity.ZzDictionaryWords;
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
public class ZzDictionaryWordsServiceImpl extends BaseBiz<ZzDictionaryWordsDao, ZzDictionaryWords> implements ZzDictionaryWordsService {
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
        //return this.zzDictionaryWordsDao.queryById(id);
        ZzDictionaryWords entity = new ZzDictionaryWords();
        entity.setId(id);
        return  super.selectOne(entity);
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

    public void insert(ZzDictionaryWords zzDictionaryWords) {
        //int insert = this.zzDictionaryWordsDao.insert(zzDictionaryWords);
        //return insert;
        super.insert(zzDictionaryWords);
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 修改数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象
     */
    @Override
    public void update(ZzDictionaryWords zzDictionaryWords) {
        //int update = this.zzDictionaryWordsDao.update(zzDictionaryWords);
        //return update;
        super.updateById(zzDictionaryWords);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public void deleteById(String id) {
        //return this.zzDictionaryWordsDao.deleteById(id) > 0;
        ZzDictionaryWords entity = new ZzDictionaryWords();
        entity.setId(id);
        super.delete(entity);
    }


    @Override
    public String sensitiveIndex(String txt) {
        ZzDictionaryWords zzDictionaryWords = new ZzDictionaryWords();
        zzDictionaryWords.setWordType("SENSITIVE");
        zzDictionaryWords.setIsUse(1);
        List<ZzDictionaryWords> zzDictionaryWordsList = this.zzDictionaryWordsDao.queryAll(zzDictionaryWords);
        if (null == zzDictionaryWordsList && zzDictionaryWordsList.isEmpty())return txt;
        return common.sensitiveSearch(txt,zzDictionaryWordsList);
    }

    @Override
    public String confidentialIndex(String txt) {
        ZzDictionaryWords zzDictionaryWords = new ZzDictionaryWords();
        zzDictionaryWords.setWordType("CONFIDENTIAL");
        zzDictionaryWords.setIsUse(1);
        List<ZzDictionaryWords> zzDictionaryWordsList = this.zzDictionaryWordsDao.queryAll(zzDictionaryWords);
        if (null == zzDictionaryWordsList && zzDictionaryWordsList.isEmpty())return "";

//        Set<String> strSet = new HashSet<String>();
//        zzDictionaryWordsList.stream().forEach(zzDictionaryWordsListfor ->{
//            strSet.add(zzDictionaryWordsListfor.getWordName());
//        });
        return common.stringSearch(txt, zzDictionaryWordsList);
    }
}
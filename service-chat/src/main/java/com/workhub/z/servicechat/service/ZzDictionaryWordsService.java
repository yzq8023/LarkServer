package com.workhub.z.servicechat.service;

import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import java.util.List;

/**
 * 字典词汇表(ZzDictionaryWords)表服务接口
 *
 * @author makejava
 * @since 2019-05-17 14:56:57
 */
public interface ZzDictionaryWordsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ZzDictionaryWords queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ZzDictionaryWords> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象
     */
    Integer insert(ZzDictionaryWords zzDictionaryWords);

    /**
     * 修改数据
     *
     * @param zzDictionaryWords 实例对象
     * @return 实例对象
     */
    Integer update(ZzDictionaryWords zzDictionaryWords);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 检索文本是否涉密（涉密类型）
     * @return
     */
    String confidentialIndex(String txt);

    /**
     * 检索文本是否敏感词汇(敏感词汇)
     * @param txt
     * @return
     */
    String sensitiveIndex(String txt);
}
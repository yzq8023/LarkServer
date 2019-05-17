package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 字典词汇表(ZzDictionaryWords)表控制层
 *
 * @author makejava
 * @since 2019-05-17 14:56:57
 */
@RestController
@RequestMapping("zzDictionaryWords")
public class ZzDictionaryWordsController {
    /**
     * 服务对象
     */
    @Resource
    private ZzDictionaryWordsService zzDictionaryWordsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzDictionaryWords selectOne(String id) {
        return this.zzDictionaryWordsService.queryById(id);
    }

}
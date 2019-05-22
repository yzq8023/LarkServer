package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 字典词汇表(ZzDictionaryWords)表控制层
 *
 * @author makejava
 * @since 2019-05-17 14:56:57
 */
@RestController
@RequestMapping("/zzDictionaryWords")
public class ZzDictionaryWordsController {
    /**
     * 服务对象
     */
    @Autowired
    private ZzDictionaryWordsService zzDictionaryWordsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public ZzDictionaryWords selectOne(String id) {
        return this.zzDictionaryWordsService.queryById(id);
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(ZzDictionaryWords zzDictionaryWords,@RequestParam("token")String token){
        zzDictionaryWords.setId(RandomId.getUUID());
        zzDictionaryWords.setCreateUser("");//TODO token 拿登陆人信息
        zzDictionaryWords.setCreateTime(new Date());
        Integer insert = this.zzDictionaryWordsService.insert(zzDictionaryWords);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (insert == 0){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/update")
    public ObjectRestResponse update(ZzDictionaryWords zzDictionaryWords, @RequestParam("token")String token){
        zzDictionaryWords.setCreateUser("");//TODO token 拿登陆人信息
        zzDictionaryWords.setUpdateTime(new Date());
        Integer update = this.zzDictionaryWordsService.update(zzDictionaryWords);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("id")String id){
        boolean flag = this.zzDictionaryWordsService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }
}
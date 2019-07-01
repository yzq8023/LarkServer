package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 字典词汇表(ZzDictionaryWords)表控制层
 *
 * @author makejava
 * @since 2019-05-17 14:56:57
 */
@RestController
@RequestMapping("/zzDictionaryWords")
public class ZzDictionaryWordsController{
    private static Logger log = LoggerFactory.getLogger(ZzDictionaryWordsController.class);
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
    public ObjectRestResponse selectOne(String id) {
        ZzDictionaryWords data = this.zzDictionaryWordsService.queryById(id);
        ObjectRestResponse res = new ObjectRestResponse();
        res.msg("200");
        res.rel(true);
        res.data(data);
        return res;
        //return this.zzDictionaryWordsService.queryById(id);
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(@RequestBody ZzDictionaryWords zzDictionaryWords,@RequestParam("token")String token){
        zzDictionaryWords.setId(RandomId.getUUID());
        zzDictionaryWords.setCreateTime(new Date());
        try{
            common.putEntityNullToEmptyString(zzDictionaryWords);
        }catch(Exception e){
            e.printStackTrace();
            log.error(common.getExceptionMessage(e));
        }
        if(zzDictionaryWords.getIsUse()==null||zzDictionaryWords.getIsUse().equals("")){
            zzDictionaryWords.setIsUse("1");
        }
        this.zzDictionaryWordsService.insert(zzDictionaryWords);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
//        if (insert == 0){
//            objectRestResponse.data("失败");
//            return objectRestResponse;
//        }
        objectRestResponse.msg("200");
        objectRestResponse.rel(true);
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/update")
    public ObjectRestResponse update(@RequestBody ZzDictionaryWords zzDictionaryWords, @RequestParam("token")String token){
        zzDictionaryWords.setUpdateTime(new Date());
        try{
            common.putEntityNullToEmptyString(zzDictionaryWords);
        }catch(Exception e){
            e.printStackTrace();
        }
        /*Integer update = this.zzDictionaryWordsService.update(zzDictionaryWords);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }*/
        try{
            common.putEntityNullToEmptyString(zzDictionaryWords);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.zzDictionaryWordsService.update(zzDictionaryWords);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.msg("200");
        objectRestResponse.rel(true);
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("id")String id){
        /*boolean flag = this.zzDictionaryWordsService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);*/
        this.zzDictionaryWordsService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.msg("200");
        objectRestResponse.rel(true);
        objectRestResponse.data("成功");
        return objectRestResponse;
    }
}
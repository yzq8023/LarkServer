package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.workhub.z.servicechat.VO.NoReadVo;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzMsgReadRelation;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.service.ZzMsgReadRelationService;
import com.workhub.z.servicechat.service.impl.ZzMsgReadRelationServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息阅读状态关系表(ZzMsgReadRelation)表控制层
 *
 * @author makejava
 * @since 2019-05-23 13:27:22
 */
@RestController
@RequestMapping("/zzMsgReadRelation")
public class ZzMsgReadRelationController
        extends BaseController<ZzMsgReadRelationServiceImpl,ZzMsgReadRelation > {
    /**
     * 服务对象
     */
    @Resource
    private ZzMsgReadRelationService zzMsgReadRelationService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzMsgReadRelation selectOne(String id) {
        return this.zzMsgReadRelationService.queryById(id);
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(ZzMsgReadRelation zzMsgReadRelation){
        zzMsgReadRelation.setId(RandomId.getUUID());
//        Integer insert = this.zzMsgReadRelationService.insert(zzMsgReadRelation);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
//        if (insert == null){
//            objectRestResponse.data("失败");
//            return objectRestResponse;
//        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("sender")String sender,
                                     @RequestParam("consumer")String consumer
                                     ){
        boolean flag = this.zzMsgReadRelationService.deleteByConsumerAndSender(sender,consumer);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }

    @PostMapping("/querynoreadcount")
    public ListRestResponse queryNoReadCount(@RequestParam("consumer")String consumer){
        Long count = this.zzMsgReadRelationService.queryNoReadCount(consumer);
        return new ListRestResponse("",0,count);
    }

    @PostMapping("/querynoreadcountlist")
    public ListRestResponse queryNoReadCountList(@RequestParam("consumer")String consumer){
        List<NoReadVo> list = this.zzMsgReadRelationService.queryNoReadCountList(consumer);
        return new ListRestResponse("",0,list);
    }
    /**
    *@Description: 清除未读标识
    *@Param:
    *@return:
    *@Author: 忠
    *@date: 2019/6/12
    */
    @PostMapping("/deleteBySender")
    public void deleteBySender(@RequestParam("sender")String sender,@RequestParam("receiver") String receiver){
        this.zzMsgReadRelationService.deleteByConsumerAndSender(sender,receiver);
    }
}
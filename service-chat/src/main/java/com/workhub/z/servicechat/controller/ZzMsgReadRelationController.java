package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzMsgReadRelation;
import com.workhub.z.servicechat.service.ZzMsgReadRelationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息阅读状态关系表(ZzMsgReadRelation)表控制层
 *
 * @author makejava
 * @since 2019-05-23 13:27:22
 */
@RestController
@RequestMapping("zzMsgReadRelation")
public class ZzMsgReadRelationController {
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

}
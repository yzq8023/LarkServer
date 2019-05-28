package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.rest.BaseController;
import com.workhub.z.servicechat.entity.ZzMsgTabRelation;
import com.workhub.z.servicechat.service.ZzMsgTabRelationService;
import com.workhub.z.servicechat.service.impl.ZzMsgTabRelationServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 标记消息关系表(ZzMsgTabRelation)表控制层
 *
 * @author makejava
 * @since 2019-05-23 16:12:40
 */
@RestController
@RequestMapping("zzMsgTabRelation")
public class ZzMsgTabRelationController
        extends BaseController<ZzMsgTabRelationServiceImpl, ZzMsgTabRelation> {
    /**
     * 服务对象
     */
    @Resource
    private ZzMsgTabRelationService zzMsgTabRelationService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzMsgTabRelation selectOne(String id) {
        return this.zzMsgTabRelationService.queryById(id);
    }

}
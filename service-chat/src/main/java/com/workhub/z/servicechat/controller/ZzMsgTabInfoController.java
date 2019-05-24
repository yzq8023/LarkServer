package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzMsgTabInfo;
import com.workhub.z.servicechat.service.ZzMsgTabInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息标记信息表(ZzMsgTabInfo)表控制层
 *
 * @author makejava
 * @since 2019-05-23 16:46:13
 */
@RestController
@RequestMapping("zzMsgTabInfo")
public class ZzMsgTabInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ZzMsgTabInfoService zzMsgTabInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzMsgTabInfo selectOne(String id) {
        return this.zzMsgTabInfoService.queryById(id);
    }

}
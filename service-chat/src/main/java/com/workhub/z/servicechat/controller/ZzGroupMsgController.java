package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 群组消息表(ZzGroupMsg)表控制层
 *
 * @author 忠
 * @since 2019-05-10 11:38:02
 */
@RestController
@RequestMapping("zzGroupMsg")
public class ZzGroupMsgController {
    /**
     * 服务对象
     */
    @Resource
    private ZzGroupMsgService zzGroupMsgService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzGroupMsg selectOne(String id) {
        return this.zzGroupMsgService.queryById(id);
    }

}
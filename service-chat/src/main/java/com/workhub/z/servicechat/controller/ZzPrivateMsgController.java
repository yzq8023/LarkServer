package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzPrivateMsg;
import com.workhub.z.servicechat.service.ZzPrivateMsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 私人消息(ZzPrivateMsg)表控制层
 *
 * @author 忠
 * @since 2019-05-13 10:57:46
 */
@RestController
@RequestMapping("zzPrivateMsg")
public class ZzPrivateMsgController {
    /**
     * 服务对象
     */
    @Resource
    private ZzPrivateMsgService zzPrivateMsgService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzPrivateMsg selectOne(String id) {
        return this.zzPrivateMsgService.queryById(id);
    }

    // TODO: 2019/5/15 插入单条数据

}
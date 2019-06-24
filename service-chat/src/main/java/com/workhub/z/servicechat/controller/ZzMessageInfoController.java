package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzMessageInfo;
import com.workhub.z.servicechat.service.ZzMessageInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息存储(ZzMessageInfo)表控制层
 *
 * @author makejava
 * @since 2019-06-23 13:50:41
 */
@RestController
@RequestMapping("zzMessageInfo")
public class ZzMessageInfoController {
    /**
     * 服务对象
     */
    @Resource
    private ZzMessageInfoService zzMessageInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzMessageInfo selectOne(String id) {
        return this.zzMessageInfoService.queryById(id);
    }

}
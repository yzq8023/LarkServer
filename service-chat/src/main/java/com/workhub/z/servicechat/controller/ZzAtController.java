package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzAt;
import com.workhub.z.servicechat.service.ZzAtService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 提及（@）功能实现(ZzAt)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:22:34
 */
@RestController
@RequestMapping("zzAt")
public class ZzAtController {
    /**
     * 服务对象
     */
    @Resource
    private ZzAtService zzAtService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzAt selectOne(String id) {
        return this.zzAtService.queryById(id);
    }

}
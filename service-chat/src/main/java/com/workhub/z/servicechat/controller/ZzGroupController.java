package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.service.ZzGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 群组表(ZzGroup)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:29:33
 */
@RestController
@RequestMapping("zzGroup")
public class ZzGroupController {
    /**
     * 服务对象
     */
    @Resource
    private ZzGroupService zzGroupService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzGroup selectOne(String id) {
        return this.zzGroupService.queryById(id);
    }

}
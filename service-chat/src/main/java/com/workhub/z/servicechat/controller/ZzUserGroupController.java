package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户群组映射表(ZzUserGroup)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:22:54
 */
@RestController
@RequestMapping("zzUserGroup")
public class ZzUserGroupController {
    /**
     * 服务对象
     */
    @Resource
    private ZzUserGroupService zzUserGroupService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzUserGroup selectOne(String id) {
        return this.zzUserGroupService.queryById(id);
    }

}
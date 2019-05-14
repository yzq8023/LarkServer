package com.workhub.z.servicechat.controller;

import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 群文件(ZzGroupFile)表控制层
 *
 * @author 忠
 * @since 2019-05-13 10:59:08
 */
@RestController
@RequestMapping("zzGroupFile")
public class ZzGroupFileController {
    /**
     * 服务对象
     */
    @Resource
    private ZzGroupFileService zzGroupFileService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public ZzGroupFile selectOne(String id) {
        return this.zzGroupFileService.queryById(id);
    }

    // TODO: 2019/5/14 群文件查询

    // TODO: 2019/5/14 文件删除（数据库）

}
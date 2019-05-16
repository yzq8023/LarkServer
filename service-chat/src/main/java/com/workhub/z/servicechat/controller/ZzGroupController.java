package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.service.ZzGroupService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 群组表(ZzGroup)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:29:33
 */
@RestController
@RequestMapping("/zzGroup")
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
    @GetMapping("/selectOne")
    public ZzGroup selectOne(String id) {

        return this.zzGroupService.queryById(id);
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(ZzGroup zzGroup,@RequestParam("token")String token){
        zzGroup.setGroupId(RandomId.getUUID());
        zzGroup.setCreator("");//TODO token 拿登陆人信息
        zzGroup.setCreateTime(new Date());
        Integer insert = this.zzGroupService.insert(zzGroup);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (insert == 0){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/update")
    public ObjectRestResponse update(ZzGroup zzGroup,@RequestParam("token")String token){
        zzGroup.setUpdator("");//TODO token 拿登陆人信息
        zzGroup.setUpdateTime(new Date());
        Integer update = this.zzGroupService.update(zzGroup);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("id")String id){
        boolean flag = this.zzGroupService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }
}
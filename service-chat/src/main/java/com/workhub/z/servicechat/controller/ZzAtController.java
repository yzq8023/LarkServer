package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzAt;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.service.ZzAtService;
import com.workhub.z.servicechat.service.impl.ZzAtServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 提及（@）功能实现(ZzAt)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:22:34
 */
@RestController
@RequestMapping("/zzAt")
public class ZzAtController extends BaseController<ZzAtServiceImpl,ZzAt>{
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
    @GetMapping("/selectOne")
    public ObjectRestResponse<ZzAt> selectOne(@RequestParam("id") String id) {
        return new ObjectRestResponse().data(this.zzAtService.queryById(id)).msg("").rel(true);
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(ZzAt zzAt){
        zzAt.setId(RandomId.getUUID());
//        Integer insert = this.zzAtService.insert(zzAt);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
//        if (insert == 0){
//            objectRestResponse.data("失败");
//            return objectRestResponse;
//        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/update")
    public ObjectRestResponse update(ZzAt zzAt, @RequestParam("token")String token){
        Integer update = this.zzAtService.update(zzAt);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == 0){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("id")String id){
        boolean flag = this.zzAtService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }
}
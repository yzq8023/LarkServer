package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 群组消息表(ZzGroupMsg)表控制层
 *
 * @author 忠
 * @since 2019-05-10 11:38:02
 */
@RestController
@RequestMapping("/zzGroupMsg")
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
    @GetMapping("/selectOne")
    public ZzGroupMsg selectOne(@RequestParam("id") String id) {
        return this.zzGroupMsgService.queryById(id);
    }

    /**
     * 文件删除(删记录)
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ObjectRestResponse delFileInfo(@RequestParam("id") String id){
        boolean flag = this.zzGroupMsgService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }

    /**
     * 创建
     * @param zzGroupMsg
     * @return
     */
    @PostMapping("/create")
    public ObjectRestResponse insert(ZzGroupMsg zzGroupMsg){
        zzGroupMsg.setMsgId(RandomId.getUUID());
        Integer insert = this.zzGroupMsgService.insert(zzGroupMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (insert == 0){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    /**
     * 修改
     * @param zzGroupMsg
     * @return
     */
    @PostMapping("/update")
    public ObjectRestResponse update(@RequestParam("zzGroupFile") ZzGroupMsg zzGroupMsg){
        Integer update = this.zzGroupMsgService.update(zzGroupMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }
}
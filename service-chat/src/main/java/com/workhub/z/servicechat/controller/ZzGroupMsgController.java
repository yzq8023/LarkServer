package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import com.workhub.z.servicechat.service.impl.ZzGroupMsgServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群组消息表(ZzGroupMsg)表控制层
 *
 * @author 忠
 * @since 2019-05-10 11:38:02
 */
@RestController
@RequestMapping("/zzGroupMsg")
public class ZzGroupMsgController
        extends BaseController<ZzGroupMsgServiceImpl, ZzGroupMsg> {
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
       /* boolean flag = this.zzGroupMsgService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);*/
        this.zzGroupMsgService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data("成功");
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
        try{
            common.putEntityNullToEmptyString(zzGroupMsg);
        }catch(Exception e){
            e.printStackTrace();
        }
//        Integer insert = this.zzGroupMsgService.insert(zzGroupMsg);
        this.zzGroupMsgService.insert(zzGroupMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
//        if (insert == 0){
//            objectRestResponse.data("失败");
//            return objectRestResponse;
//        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    /**
     * 修改
     * @param zzGroupMsg
     * @return
     */
    @PostMapping("/update")
    public ObjectRestResponse update(ZzGroupMsg zzGroupMsg){
        /*Integer update = this.zzGroupMsgService.update(zzGroupMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");*/
        zzGroupMsg.setMsgId("1");
        try{
            common.putEntityNullToEmptyString(zzGroupMsg);
        }catch(Exception e){
            e.printStackTrace();
        }
        this.zzGroupMsgService.update(zzGroupMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data("成功");
        return objectRestResponse;
    }
    /**
     * 查询消息记录（最近+历史+二者并集）
     * @auther zhuqz
     * @param sender：发送人id；receiver：群id；begin_time：发送开始时间yyyy-mm-dd;end_time：发送结束时间
     * @return
     */
    @GetMapping("/queryMsg")
    public List<ZzGroupMsg> queryMsg(@RequestParam("sender") String sender,
                                     @RequestParam("receiver") String receiver,
                                     @RequestParam("begin_time") String begin_time,
                                     @RequestParam("end_time") String end_time){

        Map<String,String> param = new HashMap<>();
        param.put("sender",sender);
        param.put("receiver",receiver);
        param.put("begin_time",begin_time);
        param.put("end_time",end_time);
        List<ZzGroupMsg> dataList=null;
        try {
            dataList=this.zzGroupMsgService.queryMsg(param);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataList;
    }
}
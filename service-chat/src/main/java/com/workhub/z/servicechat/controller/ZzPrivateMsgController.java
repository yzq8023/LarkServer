package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzPrivateMsg;
import com.workhub.z.servicechat.service.ZzPrivateMsgService;
import com.workhub.z.servicechat.service.impl.ZzPrivateMsgServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 私人消息(ZzPrivateMsg)表控制层
 *
 * @author 忠
 * @since 2019-05-13 10:57:46
 */
@RestController
@RequestMapping("/zzPrivateMsg")
public class ZzPrivateMsgController
        extends BaseController<ZzPrivateMsgServiceImpl, ZzPrivateMsg> {
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
    @GetMapping("/selectOne")
    public ZzPrivateMsg selectOne(String id) {
        return this.zzPrivateMsgService.queryById(id);
    }

    /**
     * 文件删除(删记录)
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ObjectRestResponse delFileInfo(@RequestParam("id") String id){
        boolean flag = this.zzPrivateMsgService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }

    /**
     * 创建
     * @param zzPrivateMsg
     * @return
     */
    @PostMapping("/create")
    public ObjectRestResponse insert(ZzPrivateMsg zzPrivateMsg){
        zzPrivateMsg.setMsgId(RandomId.getUUID());
//        Integer insert = this.zzPrivateMsgService.insert(zzPrivateMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
//        if (insert == null){
//            objectRestResponse.data("失败");
//            return objectRestResponse;
//        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    /**
     * 修改
     * @param zzPrivateMsg
     * @return
     */
    @PostMapping("/update")
    public ObjectRestResponse update(@RequestParam("zzGroupFile") ZzPrivateMsg zzPrivateMsg){
        Integer update = this.zzPrivateMsgService.update(zzPrivateMsg);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }
    /**
     * 查询消息记录（最近+历史+二者并集）
     * @auther zhuqz
     * @param sender：发送人id；receiver：接收人id；begin_time：发送开始时间yyyy-mm-dd;end_time：发送结束时间
     * @return
     */
    @GetMapping("/queryMsg")
    public List<ZzPrivateMsg> queryMsg(@RequestParam("sender") String sender,
                                     @RequestParam("receiver") String receiver,
                                     @RequestParam("begin_time") String begin_time,
                                     @RequestParam("end_time") String end_time){

        Map<String,String> param = new HashMap<>();
        param.put("sender",sender);
        param.put("receiver",receiver);
        param.put("begin_time",begin_time);
        param.put("end_time",end_time);
        List<ZzPrivateMsg> dataList=null;
        try {
            dataList=this.zzPrivateMsgService.queryMsg(param);
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataList;
    }
}
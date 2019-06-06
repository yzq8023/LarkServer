package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import static com.workhub.z.servicechat.config.VoToEntity.*;

@Service
public class ProcessGroupMsg extends AbstractMsgProcessor {

    @Autowired
    protected ZzGroupMsgService groupMsgService;

    public boolean sendMsg(ChannelContext channelContext, String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
//        String code = jsonObject.getString("code");
        String message = jsonObject.getString("data");
        ZzGroupMsg zzGroupMsg = (ZzGroupMsg)GroupMsgVOToModel(message);
        Tio.sendToGroup(channelContext.getGroupContext(),zzGroupMsg.getMsgReceiver(),this.getWsResponse(msg));
        saveMsg(zzGroupMsg);
        return true;
    }

    public void saveMsg(ZzGroupMsg zzGroupMsg){
        groupMsgService.insert(zzGroupMsg);
    }
}

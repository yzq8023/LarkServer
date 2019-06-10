package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.VO.GroupEditVO;
import com.workhub.z.servicechat.entity.ZzPrivateMsg;
import com.workhub.z.servicechat.server.IworkServerConfig;
import com.workhub.z.servicechat.service.ZzPrivateMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import static com.workhub.z.servicechat.config.MessageType.*;
import static com.workhub.z.servicechat.config.VoToEntity.*;

@Service
public class ProcessPrivateMsg extends AbstractMsgProcessor{

    @Autowired
    protected ZzPrivateMsgService privateMsgService;

    // TODO: 2019/5/31 判断对方是否在线

    // TODO: 2019/5/31 消息发送

    public boolean sendMsg(ChannelContext channelContext, String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
//        String code = jsonObject.getString("code");
        String message = jsonObject.getString("data");
        ZzPrivateMsg privateMsg = (ZzPrivateMsg)MsgVOToModel(message);
        saveMsg(privateMsg);
//        如果不在线则不发
        Boolean temp =  checkUserOnline(channelContext,privateMsg.getMsgReceiver());
//        Boolean temp =  Tio.sendToUser(channelContext.getGroupContext(),privateMsg.getMsgSender(),this.getWsResponse(message));
        if (temp) {
            Tio.sendToUser(channelContext.getGroupContext(),privateMsg.getMsgReceiver(),this.getWsResponse(msg));

        }else {

        }
        return true;
    }
    // TODO: 2019/5/31 存储到数据库
    public void saveMsg(ZzPrivateMsg privateMsg){
        privateMsgService.insert(privateMsg);
    }

    

}

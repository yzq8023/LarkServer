package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.VO.GroupEditVO;
import com.workhub.z.servicechat.entity.ZzPrivateMsg;
import com.workhub.z.servicechat.server.IworkServerConfig;
import com.workhub.z.servicechat.service.ZzPrivateMsgService;
import org.apache.commons.io.TaggedIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import static com.workhub.z.servicechat.config.VoToEntity.*;

@Service
public class ProcessPrivateMsg extends AbstractMsgProcessor{

    @Autowired
    protected ZzPrivateMsgService privateMsgService;

    public boolean sendMsg(ChannelContext channelContext, String msg) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(msg);

//      String code = jsonObject.getString("code");
        String message = jsonObject.getString("data");
        ZzPrivateMsg privateMsg = (ZzPrivateMsg)MsgVOToModel(message);
        saveMsg(privateMsg);
//      存储消息信息（新）
        super.saveMessageInfo("USER",privateMsg.getMsgSender(),privateMsg.getMsgReceiver()
                ,privateMsg.getLevels(),privateMsg.getSendTime(),message,privateMsg.getMsgId());
        super.msgAnswer(msg,privateMsg.getMsgId(),channelContext);
//      如果不在线则不发
        Boolean temp =  checkUserOnline(channelContext,privateMsg.getMsgReceiver());
        if (true) {
            Tio.sendToUser(channelContext.getGroupContext(),privateMsg.getMsgReceiver(),this.getWsResponse(msg));
        }else {

        }
        return true;
    }

    public void saveMsg(ZzPrivateMsg privateMsg){
        privateMsgService.insert(privateMsg);
        super.saveNoReadMsg(privateMsg.getMsgSender(),privateMsg.getMsgReceiver());
    }
}

package com.workhub.z.servicechat.processor;

import com.workhub.z.servicechat.entity.ZzPrivateMsg;
import com.workhub.z.servicechat.server.IworkServerConfig;
import com.workhub.z.servicechat.service.ZzPrivateMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;


import static com.workhub.z.servicechat.config.VoToEntity.*;

public class ProcessPrivateMsg extends AbstractMsgProcessor{

    private ZzPrivateMsg privateMsg;
    @Autowired
    protected ZzPrivateMsgService privateMsgService;

    ProcessPrivateMsg(ChannelContext channelContext, String message) throws Exception {
        this.privateMsg = (ZzPrivateMsg)MsgVOToModel(message);
        sendMsg(channelContext, message);
        saveMsg();
    }

    // TODO: 2019/5/31 判断对方是否在线

    // TODO: 2019/5/31 消息发送
    public void sendMsg(ChannelContext channelContext, String message){
        Tio.sendToUser(channelContext.getGroupContext(),privateMsg.getMsgReceiver(),this.getWsResponse(message));
    }
    // TODO: 2019/5/31 存储到数据库 
    public void saveMsg(){
        privateMsgService.insert(this.privateMsg);
    }
    //
    

}

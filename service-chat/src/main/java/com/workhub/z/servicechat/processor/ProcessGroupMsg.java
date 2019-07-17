package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import com.workhub.z.servicechat.service.ZzGroupService;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import java.util.List;

import static com.workhub.z.servicechat.config.VoToEntity.*;

@Service
public class ProcessGroupMsg extends AbstractMsgProcessor {

    @Autowired
    protected ZzGroupMsgService groupMsgService;
    @Autowired
    protected ZzGroupService groupService;

    public boolean sendMsg(ChannelContext channelContext, String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
//      String code = jsonObject.getString("code");
        String message = jsonObject.getString("data");
        ZzGroupMsg zzGroupMsg = (ZzGroupMsg)GroupMsgVOToModel(message);
        Tio.sendToGroup(channelContext.getGroupContext(),zzGroupMsg.getMsgReceiver(),this.getWsResponse(msg));
        saveMsg(zzGroupMsg);
        //存储消息信息（新）
        super.saveMessageInfo("GROUP",zzGroupMsg.getMsgSender(),zzGroupMsg.getMsgReceiver()
                ,zzGroupMsg.getLevels(),zzGroupMsg.getSendTime(),message,zzGroupMsg.getMsgId());
        return true;
    }

    public void saveMsg(ZzGroupMsg zzGroupMsg){
        groupMsgService.insert(zzGroupMsg);
        List<String> userList = groupService.queryGroupUserIdListByGroupId(zzGroupMsg.getMsgReceiver());
        if(userList == null|| userList.isEmpty()) return;
        for (int i = 0; i < userList.size() ; i++) {
            super.saveNoReadMsg(zzGroupMsg.getMsgReceiver(),userList.get(i));
        }
    }
}

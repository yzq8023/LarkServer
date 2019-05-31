package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.server.IworkServerConfig;
import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsResponse;

import static com.workhub.z.servicechat.config.MessageType.*;
import static com.workhub.z.servicechat.config.MessageType.SYS_MSG;

public class ProcessMsg {
    /**
     * 消息处理入口
     */
    public static Object process(ChannelContext channelContext, String msg) {
        try{
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("data");

//            processMsg(channelContext,msg,Integer.parseInt(code));

            switch (Integer.parseInt(code)){
//                case SYS_MSG:
//                    Tio.sendToAll(channelContext.getGroupContext(),wsResponse);
//                    break;
//                case GROUP_MSG:
//                    ZzGroupMsg groupMsg = (ZzGroupMsg)GroupMsgVOToModel(msg);
//                    serverHandler.groupMsgService.insert(groupMsg);
//                    Tio.bSendToGroup(channelContext.getGroupContext(), Const.GROUP_SYS, wsResponse);
//                    break;
                case PRIVATE_MSG:
                    return new ProcessPrivateMsg(channelContext,message);
                    //                case GROUP_JOIN_MSG:
//                    UserGroupDto userGroupDto = JSON.parseObject(msg, UserGroupDto.class);
//                    Tio.bindGroup(channelContext,userGroupDto.getGroupId());
//                    break;
//                case GROUP_INVITE_MSG:
//                    // TODO: 2019/5/30 通用方法
//                    GroupTaskDto groupTaskDto = JSON.parseObject(msg, GroupTaskDto.class);
//                    List<UserListDto> listDtos = groupTaskDto.getUserList();
//                    // TODO: 2019/5/30 定义通知格式
//                    for (int i = 0; i < listDtos.size(); i++) {
//                        Tio.sendToUser(channelContext.getGroupContext(),listDtos.get(i).getUserId(),wsResponse);
//                    }
//                    break;
                default:
                    System.out.println("你说的什么鬼");
                    break;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}

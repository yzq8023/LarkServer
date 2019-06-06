package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.workhub.z.servicechat.VO.GroupEditVO;
import com.workhub.z.servicechat.model.UserGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import java.util.List;

import static com.workhub.z.servicechat.config.MessageType.*;

@Service
public class ProcessMsg {

    @Autowired
    private ProcessPrivateMsg processPrivateMsg;
    @Autowired
    private ProcessEditGroup processEditGroup;
    @Autowired
    private ProcessGroupMsg processGroupMsg;
    /**
     * 消息处理入口
     */

    public Object process(ChannelContext channelContext, String msg) {
        try{
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("data");

//            processMsg(channelContext,msg,Integer.parseInt(code));

            switch (Integer.parseInt(code)){
                case SYS_MSG:
//                    Tio.sendToAll(channelContext.getGroupContext(),wsResponse);
                    break;
                case GROUP_MSG:
                   return processGroupMsg.sendMsg(channelContext,msg);
                case PRIVATE_MSG:
                    return  processPrivateMsg.sendMsg(channelContext,msg);
                    //
                 case GROUP_EDIT:
                     processEditGroup.processManage(channelContext,message);
//                    GroupEditVO groupEditVO = JSON.parseObject(message, GroupEditVO.class);
//                     JSONArray datas = jsonObject.getJSONArray("data");
//                     List<GroupEditVO> groupEditVO = JSON.parseObject(datas.toJSONString(), new TypeReference<List<GroupEditVO>>() {
//                     });
//                     Tio.bindGroup(channelContext,groupEditVO.getGroupId());
                    break;
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
            System.out.println("你说错了");
            return null;
        }
        return null;
    }
}

package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.service.ZzGroupService;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;

import static com.workhub.z.servicechat.config.MessageType.*;

@Service
public class ProcessMsg{

    @Autowired
    private ProcessPrivateMsg processPrivateMsg;
    @Autowired
    private ProcessEditGroup processEditGroup;
    @Autowired
    private ProcessGroupMsg processGroupMsg;
    @Autowired
    private ZzUserGroupService userGroupService;
    @Autowired
    private ZzGroupService groupService;
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
                case GROUP_CREATE:
                    return processEditGroup.createGroup(channelContext,msg);

                default:
                    System.out.println("你说的什么鬼");
                    break;
            }
        }catch (Exception e){
            System.out.println("别提了又错了"+ e.getMessage());
            return null;
        }
        return null;
    }
}

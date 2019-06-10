package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.workhub.z.servicechat.VO.GroupEditVO;
import com.workhub.z.servicechat.config.ImageUtil;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.model.UserGroupDto;
import com.workhub.z.servicechat.model.UserListDto;
import com.workhub.z.servicechat.service.ZzGroupService;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.workhub.z.servicechat.config.MessageType.*;
import static com.workhub.z.servicechat.config.RandomId.getUUID;

@Service
public class ProcessMsg {

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
                    return createGroup(channelContext,message);

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


    public boolean createGroup(ChannelContext channelContext,String message) throws IOException {
        ZzGroup zzGroup = new ZzGroup();
        JSONObject groupJson = JSONObject.parseObject(message);
        zzGroup.setGroupId(getUUID());
        zzGroup.setGroupName(groupJson.getString("groupName"));
        zzGroup.setCreator(groupJson.getString("creator"));
        zzGroup.setGroupDescribe(groupJson.getString("groupDescribe"));
        zzGroup.setUpdator(groupJson.getString("updator"));
        zzGroup.setPname(groupJson.getString("pname"));
        zzGroup.setScop(groupJson.getString("scop"));
        zzGroup.setLevels(groupJson.getString("levels"));

        ArrayList<String> picUrls = new ArrayList<>();
        List<UserListDto> userList = new ArrayList<UserListDto>();
        JSONArray userJsonArray = JSONObject.parseArray(groupJson.getString("userList"));
        for (int i = 0; i < userJsonArray.size(); i++) {
            JSONObject userJson = JSONObject.parseObject(userJsonArray.getString(i));
            picUrls.add(userJson.getString("img"));
            ZzUserGroup zzUserGroup = new ZzUserGroup();
            zzUserGroup.setId(UUIDUtils.generateShortUuid());
            zzUserGroup.setGroupId(zzGroup.getGroupId());
            zzUserGroup.setUserId(userJson.getString("userId"));
            userGroupService.insert(zzUserGroup);
            UserListDto userListDto = new UserListDto();
            userListDto.setUserId(userJson.getString("userId"));
            userListDto.setUserLevels(userJson.getString("userLevels"));
            userListDto.setImg(userJson.getString("img"));
            userList.add(userListDto);
        }
        String newPath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\"+zzGroup.getGroupId()+".jpg";
        ImageUtil.generate(picUrls, newPath);
        zzGroup.setGroupImg(newPath);
        groupService.insert(zzGroup);//创建讨论组
        // TODO: 2019/6/3 群头像生成
        return true;
    }
}

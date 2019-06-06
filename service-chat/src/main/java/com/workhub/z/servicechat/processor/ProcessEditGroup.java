package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.workhub.z.servicechat.VO.GroupEditVO;
import com.workhub.z.servicechat.config.ImageUtil;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.model.GroupTaskDto;
import com.workhub.z.servicechat.model.UserListDto;
import com.workhub.z.servicechat.service.ZzGroupService;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.workhub.z.servicechat.config.MessageType.*;
import static com.workhub.z.servicechat.config.RandomId.getUUID;
import static com.workhub.z.servicechat.config.VoToEntity.*;

@Service
public class ProcessEditGroup extends AbstractMsgProcessor{

    @Autowired
    ZzUserGroupService userGroupService;
    @Autowired
    ZzGroupService groupService;

    // TODO: 2019/6/4 分类处理群组编辑
    public void processManage(ChannelContext channelContext, String message) throws IOException {
        GroupTaskDto groupTaskDto = toGroupTaskDto(message);
        switch (groupTaskDto.getType()){
            case GROUP_JOIN_MSG:
                // TODO: 2019/6/4 处理加入群组消息，1绑定用户到群组
                Tio.bindGroup(channelContext,groupTaskDto.getGroupId());
                joinGroup(channelContext,groupTaskDto);
//                groupTaskDto.
                break;
            case GROUP_CREATE_MSG:
                // TODO: 2019/6/4 1.生成群组头像 2.存入数据库 3.向用户分发加入群组消息
                createGroup(channelContext,message);
                break;
            case GROUP_EXIT_MSG:
                // TODO: 2019/6/4 退出群组
                    break;
            case GROUP_CLOSE_MSG:
                break;
        }
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

    public boolean joinGroup(ChannelContext channelContext,GroupTaskDto groupTaskDto){
        for ( UserListDto userInfo:groupTaskDto.getUserList()){
            ZzUserGroup userGroup = new ZzUserGroup();
            userGroup.setCreatetime(new Date());
            userGroup.setId(getUUID());
            userGroup.setGroupId(groupTaskDto.getGroupId());
            userGroup.setUserId(userInfo.getUserId());
            userGroupService.insert(userGroup);

            GroupEditVO groupEditVO = new GroupEditVO();
            groupTaskDto.setType(GROUP_JOIN_MSG);
            groupEditVO.setCode(GROUP_EDIT);
            groupTaskDto.setUserList(null);
            groupEditVO.setData(groupTaskDto);
            String msg = JSON.toJSONString(groupEditVO);
            Tio.sendToUser(channelContext.getGroupContext(),userInfo.getUserId(),this.getWsResponse(msg));
        }
        return true;
    }
    // TODO: 2019/6/3 退出群组
}

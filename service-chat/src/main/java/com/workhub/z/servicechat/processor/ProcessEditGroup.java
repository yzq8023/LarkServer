package com.workhub.z.servicechat.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import static com.workhub.z.servicechat.config.VoToEntity.toGroupTaskDto;

@Service
public class ProcessEditGroup extends AbstractMsgProcessor{

    @Autowired
    ZzUserGroupService userGroupService;
    @Autowired
    ZzGroupService groupService;

    // TODO: 2019/6/4 分类处理群组编辑
    public boolean processManage(ChannelContext channelContext, String message) throws IOException {
        GroupTaskDto groupTaskDto = toGroupTaskDto(message);
        switch (groupTaskDto.getType()){
            case GROUP_JOIN_MSG://这个分支目前走不进来了
                // TODO: 2019/6/4 处理加入群组消息，1绑定用户到群组
//                Tio.bindGroup(channelContext,groupTaskDto.getGroupId());
                try {
                    return joinGroup(channelContext,groupTaskDto);
                }catch (Exception e){
                    e.printStackTrace();
                }

//                groupTaskDto.

            case GROUP_INVITE_MSG:
                // TODO: 2019/6/4  1.存入数据库 2.生成群组头像 3.向用户分发加入群组消息
//                createGroup(channelContext,message);
                try {
                    joinGroup(channelContext,groupTaskDto);
//                    createGroupHeadsImg(groupTaskDto.getGroupId());
                }catch (Exception e){
                    e.printStackTrace();
                }
                Tio.bindGroup(channelContext,groupTaskDto.getGroupId());
                break;
            case GROUP_EXIT_MSG:
                // TODO: 2019/6/4 退出群组
                    break;
            case GROUP_CLOSE_MSG:
                break;
        }
        return true;
    }

    public boolean joinGroup(ChannelContext channelContext,GroupTaskDto groupTaskDto) throws Exception{
        //如果是加入：人员列表只有一个用户，他们自己；如果是邀请，人员列表可能多个
        for ( UserListDto userInfo:groupTaskDto.getUserList()){
            ZzUserGroup userGroup = new ZzUserGroup();
            userGroup.setCreatetime(new Date());
            userGroup.setId(getUUID());
            userGroup.setGroupId(groupTaskDto.getGroupId());
            userGroup.setUserId(userInfo.getUserId());
            userGroupService.insert(userGroup);
            //创建群头像
//            createGroupHeadsImg(groupTaskDto.getGroupId());
        }


        /*for ( UserListDto userInfo:groupTaskDto.getUserList()){
            ZzUserGroup userGroup = new ZzUserGroup();
            userGroup.setCreatetime(new Date());
            userGroup.setId(getUUID());
            userGroup.setGroupId(groupTaskDto.getGroupId());
            userGroup.setUserId(userInfo.getUserId());
            userGroupService.insert(userGroup);

            GroupEditVO groupEditVO = new GroupEditVO();
            groupTaskDto.setType(GROUP_INVITE_MSG);
            groupEditVO.setCode(GROUP_EDIT);
            groupTaskDto.setUserList(null);
            groupEditVO.setData(groupTaskDto);
            String msg = JSON.toJSONString(groupEditVO);
            Tio.sendToUser(channelContext.getGroupContext(),userInfo.getUserId(),this.getWsResponse(msg));
        }*/
        return true;
    }
    //生成群头像九宫格
    public boolean createGroupHeadsImg(String groupId) throws Exception{
        //获取群人员数量
        int groupMembers = userGroupService.getGroupUserCount(groupId);
        if(groupMembers>=10){//如果大于十个人
            return true;//返回，不必更新头像，因为之前的九个人头像已经占满了九宫格
        }
        String newGroupHeadsImgPath = "C:\\Users\\Public\\Pictures\\Sample Pictures\\"+groupId+".jpg";
        List<String> memberHeadsList = userGroupService.getGroupUserHeadList(groupId);
        ImageUtil.generate(memberHeadsList,newGroupHeadsImgPath);
        //更新数据库头像
        ZzGroup zzGroup = new ZzGroup();
        zzGroup.setGroupId(groupId);
        zzGroup.setGroupImg(newGroupHeadsImgPath);
        groupService.update(zzGroup);
        return true;
    }
    // TODO: 2019/6/3 退出群组

    //群创建
    public boolean createGroup(ChannelContext channelContext,String message) throws IOException {
        //创建群begin
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
        groupService.insert(zzGroup);//创建讨论组
        //创建群end

        //遍历用户begin
        JSONArray userJsonArray = JSONObject.parseArray(groupJson.getString("userList"));
        for (int i = 0; i < userJsonArray.size(); i++) {
            JSONObject userJson = JSONObject.parseObject(userJsonArray.getString(i));
            GroupTaskDto groupTaskDto = new GroupTaskDto();
            GroupEditVO groupEditVO = new GroupEditVO();
            groupTaskDto.setType(GROUP_INVITE_MSG);//设置返回执行的分支
            groupEditVO.setCode(GROUP_EDIT);//设置返回处理大类
            //给每个用户返回列表只有自己
            List<UserListDto> userList = new ArrayList<UserListDto>();
            UserListDto userListDto = new UserListDto();
            userListDto.setUserId(userJson.getString("userId"));
            userListDto.setUserLevels(userJson.getString("userLevels"));
            userListDto.setImg(userJson.getString("img"));
            userList.add(userListDto);
            groupTaskDto.setUserList(userList);
            groupEditVO.setData(groupTaskDto);
            String msg = JSON.toJSONString(groupEditVO);
            Tio.sendToUser(channelContext.getGroupContext(),userJson.getString("userId"),this.getWsResponse(msg));
        }
        //遍历用户end

        /*ArrayList<String> picUrls = new ArrayList<>();
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
        zzGroup.setGroupImg(newPath);*/
        // TODO: 2019/6/3 群头像生成
        return true;
    }
}

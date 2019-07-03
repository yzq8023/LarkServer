package com.workhub.z.servicechat.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.hollykunge.security.common.vo.rpcvo.ContactVO;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupInfoVO;
import com.workhub.z.servicechat.VO.GroupUserListVo;
import com.workhub.z.servicechat.VO.UserInfoVO;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.config.common;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.feign.IUserService;
import com.workhub.z.servicechat.service.ZzGroupMsgService;
import com.workhub.z.servicechat.service.ZzGroupService;
import com.workhub.z.servicechat.service.ZzMessageInfoService;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.workhub.z.servicechat.config.VoToEntity.ZzGroupToGroupInfo;

/**
 * 群组表(ZzGroup)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:29:33
 */
@RestController
@RequestMapping("/zzGroup")
public class ZzGroupController  {
    private static Logger log = LoggerFactory.getLogger(ZzGroupController.class);
    /**
     * 服务对象
     */
    @Resource
    private ZzGroupService zzGroupService;

    @Resource
    private ZzUserGroupService userGroupService;

    @Resource
    private ZzGroupMsgService groupMsgService;
    @Resource
    private ZzMessageInfoService messageInfoService;

    @Autowired
    private IUserService iUserService;
    /**
     * 通过主键查询单条数据
     *
     * @param groupId 主键
     * @return 单条数据
     */
    @GetMapping("/getGroupInfo")
    public ObjectRestResponse selectOne(@RequestParam("groupId")String groupId) throws Exception {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.msg("200");

        GroupInfoVO groupInfo = new GroupInfoVO();
        groupInfo = (GroupInfoVO)ZzGroupToGroupInfo(this.zzGroupService.queryById(groupId));
        groupInfo.setMemberNum(Math.toIntExact(this.zzGroupService.groupUserListTotal(groupId)));
        common.putVoNullStringToEmptyString(groupInfo);
        objectRestResponse.data(groupInfo);
        return objectRestResponse;
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(@RequestBody ZzGroup zzGroup,@RequestParam("token")String token){
        zzGroup.setGroupId(RandomId.getUUID());
        zzGroup.setCreateTime(new Date());
        try {
            common.putEntityNullToEmptyString(zzGroup);
        }catch (Exception e){
            log.error(common.getExceptionMessage(e));
        }
        this.zzGroupService.insert(zzGroup);
//        Integer insert = this.zzGroupService.insert(zzGroup);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
//        if (insert == 0){
//            objectRestResponse.data("失败");
//            return objectRestResponse;
//        }
        objectRestResponse.rel(true);
        objectRestResponse.msg("200");
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/update")
    public ObjectRestResponse update(@RequestBody ZzGroup zzGroup,@RequestParam("token")String token){
        zzGroup.setUpdateTime(new Date());
        try {
            common.putEntityNullToEmptyString(zzGroup);
        }catch (Exception e){
            e.getStackTrace();
        }
        Integer update = this.zzGroupService.update(zzGroup);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.rel(true);
        objectRestResponse.msg("200");
        if (update == null){
            objectRestResponse.rel(false);
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("id")String id){
        boolean flag = this.zzGroupService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }
    /**
    *@Description:
    *@Param:
    *@return:
    *@Author: 忠
    *@date: 2019/6/11
    */
    @PostMapping("/querygroupuser")
    public TableResultResponse queryGroupUserList(@RequestParam("id")String id,
                                                  @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                  @RequestParam(value = "size",defaultValue = "10")Integer size){
        PageInfo<GroupUserListVo> groupUserListVoPageInfo = null;
        try {
            groupUserListVoPageInfo = this.zzGroupService.groupUserList(id, page, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int pageSize, int pageNo, int totalPage, long totalCount, List<T> rows
        return new TableResultResponse(groupUserListVoPageInfo.getPageSize(),
                groupUserListVoPageInfo.getPageNum(),
                groupUserListVoPageInfo.getPages(),
                groupUserListVoPageInfo.getTotal(),
                groupUserListVoPageInfo.getList());
    }

    /**
    *@Description: 根据用户id查询用户所在群组信息
    *@Param: 用户id
    *@return: 群组列表
    *@Author: 忠
    *@date: 2019/6/11
    */
    @GetMapping("/queryGroupListByUserId")
    public ListRestResponse queryGroupListByUserId(@RequestParam("userId")String userId) throws Exception {
        List<ZzGroup> groups = this.zzGroupService.queryGroupListByUserId(userId);
        common.putVoNullStringToEmptyString(groups);
        return new ListRestResponse("200",groups.size(),groups);
    }

    @GetMapping("/queryContactListById")
    public ListRestResponse queryContactListById(@RequestParam("userId")String userId) throws Exception {
        List<ContactVO> contactVOS = userGroupService.getContactVOList(userId);
        common.putVoNullStringToEmptyString(contactVOS);
//        List<ZzGroup> groups = this.zzGroupService.queryGroupListByUserId(userId);
        return new ListRestResponse("200", contactVOS.size(), contactVOS);
    }

    @GetMapping("/queryHistoryMessageById")
    public ListRestResponse queryHistoryMessageById(@RequestParam("userId")String userId) throws Exception {
//        List<HistoryMessageVO> query = this.groupMsgService.queryHistoryMessageById(userId);
        String res =  messageInfoService.queryContactsMessage(userId);
        JSONArray myJsonArray = JSONArray.parseArray(res);
        return new ListRestResponse("200", 0,myJsonArray);
    }

    /**
     * 逻辑删除群
     * @param groupId 群id;delFlg：删除标记位，1删除，0 不删
     * @return  1成功；-1失败；
     * @author zhuqz
     * @since 2019-06-11
     */
    @PostMapping("/deleteGroupLogic")
    public ObjectRestResponse deleteGroupLogic(@RequestParam("groupId")String groupId,@RequestParam("delFlg")String delFlg) {
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        String oppRes = "1";
        try {
            this.zzGroupService.deleteGroupLogic(groupId,delFlg);
        }catch (Exception e){
            e.printStackTrace();
            oppRes="-1";
        }
        if("1".equals(oppRes)){
            objectRestResponse.data("成功");//成功
            objectRestResponse.rel(true);
            objectRestResponse.msg("200");
        }else{
            objectRestResponse.data("失败");//失败
            objectRestResponse.rel(false);
            objectRestResponse.msg("200");
        }
        return objectRestResponse;
    }
    //当前登录人查询具体某个人或者群的聊天记录,contactId表示个人或者群id
    @GetMapping("/queryHistoryMessageForSingle")
    public TableResultResponse queryHistoryMessageForSingle(@RequestParam("userId")String userId,@RequestParam("contactId")String contactId,@RequestParam("isGroup")String isGroup,@RequestParam("page")String page,@RequestParam("size")String size) throws Exception {
        TableResultResponse resultResponse = messageInfoService.queryHistoryMessageForSingle(userId,contactId,isGroup,page,size);
        return resultResponse;
    }
    /**
     * 查询群成员列表信息
     *
     * @param groupId 主键
     * @return 单条数据
     */
    @GetMapping("/getGroupUserList")
    public ListRestResponse getGroupUserList(@RequestParam("groupId")String groupId) throws Exception {

        String userIds= this.zzGroupService.getGroupUserList(groupId);
        List<UserInfo> list  = iUserService.userList(userIds);
        List<UserInfoVO> dataList = new ArrayList<>();
        for(UserInfo temp:list){
            UserInfoVO vo = new UserInfoVO();
            vo.setAvartar(temp.getAvatar());
            vo.setId(temp.getId());
            vo.setOnline("1");//是否在线有待后续开发
            vo.setName(temp.getName());
            dataList.add(vo);
        }
        ListRestResponse res=new ListRestResponse("200",dataList.size(),dataList);
        return  res;
    }
}
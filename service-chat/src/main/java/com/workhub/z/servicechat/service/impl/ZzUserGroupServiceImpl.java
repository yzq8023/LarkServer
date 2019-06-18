package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.vo.rpcvo.ContactVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupListVo;
import com.workhub.z.servicechat.VO.NoReadVo;
import com.workhub.z.servicechat.VO.UserNewMsgVo;
import com.workhub.z.servicechat.dao.ZzUserGroupDao;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.feign.IUserService;
import com.workhub.z.servicechat.service.ZzGroupService;
import com.workhub.z.servicechat.service.ZzMsgReadRelationService;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户群组映射表(ZzUserGroup)表服务实现类
 *
 * @author 忠
 * @since 2019-05-10 14:22:54
 */
@Service("zzUserGroupService")
public class ZzUserGroupServiceImpl extends BaseBiz<ZzUserGroupDao, ZzUserGroup> implements ZzUserGroupService {
    @Resource
    private ZzUserGroupDao zzUserGroupDao;

    @Autowired
    private ZzMsgReadRelationService zzMsgReadRelationService;

    @Autowired
    private ZzGroupService zzGroupService;

    @Autowired
    private IUserService iUserService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ZzUserGroup queryById(String id) {
        return this.zzUserGroupDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzUserGroup> queryAllByLimit(int offset, int limit) {
        return this.zzUserGroupDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzUserGroup 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public void insert(ZzUserGroup zzUserGroup) {
        int insert = this.zzUserGroupDao.insert(zzUserGroup);
//        return insert;
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 修改数据
     *
     * @param zzUserGroup 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer update(ZzUserGroup zzUserGroup) {
        int update = this.zzUserGroupDao.update(zzUserGroup);
        return update;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(String id) {
        return this.zzUserGroupDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<GroupListVo> groupUserList(String id, int page, int size) throws Exception {
        if (StringUtil.isEmpty(id)) throw new NullPointerException("id is null");
        Page<Object> pageMassage = PageHelper.startPage(page, size);
        pageMassage.setTotal(this.zzUserGroupDao.groupListTotal(id));
        int startRow = pageMassage.getStartRow();
        int endRow = pageMassage.getEndRow();
        PageInfo<GroupListVo> pageInfoGroupInfo = new PageInfo<GroupListVo>();
        pageInfoGroupInfo.setList(this.zzUserGroupDao.groupList(id,startRow,endRow));
        pageInfoGroupInfo.setTotal(pageMassage.getTotal());
        pageInfoGroupInfo.setStartRow(startRow);
        pageInfoGroupInfo.setEndRow(endRow);
        pageInfoGroupInfo.setPages(pageMassage.getPages());
        pageInfoGroupInfo.setPageNum(page);
        pageInfoGroupInfo.setPageSize(size);
        return pageInfoGroupInfo;
    }

    @Override
    public Long groupUserListTotal(String id) throws Exception {
        return this.zzUserGroupDao.groupListTotal(id);
    }

    @Override
    public List<UserNewMsgVo> getUserNewMsgList(String id) {
        return this.zzUserGroupDao.getUserNewMsgList(id);
    }

    @Override
    public List<ContactVO> getContactVOList(String id) {
        List<UserNewMsgVo> userNewMsgList = this.getUserNewMsgList(id);
        // TODO: 2019/6/12 是否@
        // TODO: 2019/6/12 私有化定制
        List<ContactVO> list = new ArrayList<ContactVO>();
        List<NoReadVo> noReadVos = zzMsgReadRelationService.queryNoReadCountList(id);
        if(userNewMsgList == null|| userNewMsgList.isEmpty()) return list;
        userNewMsgList.stream().forEach(n ->{
            ContactVO contactVO = new ContactVO();
            if (n.getTableType().equals("GROUP")) {
                ZzGroup group = new ZzGroup();
                group = zzGroupService.queryById(n.getMsgSener());

                contactVO.setId(n.getMsgSener());
                contactVO.setLastMessage(n.getMsg());
                contactVO.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(n.getSendTime()));
                contactVO.setAvatar(group.getGroupImg());
                contactVO.setName(group.getGroupName());
                contactVO.setAtMe(false);
                contactVO.setIsTop(false);
                contactVO.setIsMute(false);
                contactVO.setIsGroup(n.getTableType().equals("GROUP"));
            } else if (n.getTableType().equals("USER")) {
                UserInfo userInfo = iUserService.info(n.getMsgSener());
                contactVO.setId(n.getMsgSener());
                contactVO.setLastMessage(n.getMsg());
                contactVO.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(n.getSendTime()));
                contactVO.setAvatar(userInfo.getAvatar());
                contactVO.setName(userInfo.getName());
//                contactVO.setAtMe(false);
//                contactVO.setIsTop(false);
//                contactVO.setIsMute(false);
                contactVO.setIsGroup(n.getTableType().equals("GROUP"));
            }
            for (int j = 0; j < noReadVos.size(); j++) {
                if (noReadVos.get(j).getSender() == n.getMsgSener()){
                    contactVO.setUnreadNum(noReadVos.get(j).getMsgCount());
                }
            }
//            if (noReadVos == null|| noReadVos.isEmpty()) contactVO.setUnreadNum(0);
//            else {noReadVos.stream().forEach(m ->{
//                if (m.getSender() == userNewMsgList.get(i).getMsgSener()){
//                    contactVO.setUnreadNum(m.getMsgCount());
//                }
//            });
            list.add(contactVO);
        });
        return list;
    }
    /**
     * 修改用户群个性化信息--是否置顶
     * @param userId 用户id；groupId 群id；topFlg 1置顶，0不置顶
     * @return  1成功；0用户不在组内或者组已经不存在；-1错误
     * @author zhuqz
     * @since 2019-06-11
     */
   public String setUserGroupTop(String userId,String gourpId,String topFlg) throws Exception{
        String res="1";
        int i=this.zzUserGroupDao.setUserGroupTop( userId, gourpId, topFlg);
        if(i==0){
            res = "0";
        }
        return  res;
    }
    /**
     * 修改用户群个性化信息--是否置顶
     * @param userId 用户id；groupId 群id；muteFlg 1免打扰，0否
     * @return  1成功；0用户不在组内或者组已经不存在；-1错误
     * @author zhuqz
     * @since 2019-06-11
     */
    public String setUserGroupMute(String userId,String gourpId,String topFlg) throws Exception{
        String res="1";
        int i=this.zzUserGroupDao.setUserGroupMute( userId, gourpId, topFlg);
        if(i==0){
            res = "0";
        }
        return  res;
    }
}
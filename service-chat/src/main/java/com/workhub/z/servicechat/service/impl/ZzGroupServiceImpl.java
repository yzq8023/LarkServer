package com.workhub.z.servicechat.service.impl;

import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import com.workhub.z.servicechat.VO.GroupInfoVO;
import com.workhub.z.servicechat.VO.GroupUserListVo;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.dao.ZzGroupDao;
import com.workhub.z.servicechat.feign.IUserService;
import com.workhub.z.servicechat.service.ZzGroupService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 群组表(ZzGroup)表服务实现类
 *
 * @author 忠
 * @since 2019-05-10 14:29:32
 */
@Service("zzGroupService")
public class ZzGroupServiceImpl implements ZzGroupService {
    @Resource
    private ZzGroupDao zzGroupDao;

    @Autowired
    private IUserService iUserService;

    /**
     * 通过ID查询单条数据
     *
     * @param groupId 主键
     * @return 实例对象
     */
    @Override
    public ZzGroup queryById(String groupId) {
        return this.zzGroupDao.queryById(groupId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<ZzGroup> queryAllByLimit(int offset, int limit) {
        return this.zzGroupDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param zzGroup 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer insert(ZzGroup zzGroup) {
        int insert = this.zzGroupDao.insert(zzGroup);
        return insert;
    }

    /**
     * 修改数据
     *
     * @param zzGroup 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public Integer update(ZzGroup zzGroup) {
        int update = this.zzGroupDao.update(zzGroup);
        return update;
    }

    /**
     * 通过主键删除数据
     *
     * @param groupId 主键
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean deleteById(String groupId) {
        return this.zzGroupDao.deleteById(groupId) > 0;
    }

    @Override
    public PageInfo<GroupUserListVo> groupUserList(String id, int page, int size) throws Exception {
        if (StringUtil.isEmpty(id)) throw new NullPointerException("id is null");
        Page<Object> pageMassage = PageHelper.startPage(page, size);
        pageMassage.setTotal(this.zzGroupDao.groupUserListTotal(id));
        int startRow = pageMassage.getStartRow();
        int endRow = pageMassage.getEndRow();
        List<GroupUserListVo> groupUserListVos = this.zzGroupDao.groupUserList(id, startRow, endRow);
        PageInfo<GroupUserListVo> pageInfoGroupInfo = new PageInfo<GroupUserListVo>();
        if (groupUserListVos ==null || groupUserListVos.isEmpty()) return pageInfoGroupInfo;
        Set<String> setStr = new HashSet<String>();
        groupUserListVos.stream().forEach(groupUserListVosList -> {
            setStr.add(groupUserListVosList.getUserId());
        });
        List<UserInfo> userInfos = iUserService.userList(setStr);
        groupUserListVos.stream().forEach(groupUserListVosList ->{
            userInfos.stream().filter(userInfosFilter ->userInfosFilter.getId().equals(groupUserListVosList.getUserId())).forEach(userInfosList ->{
                groupUserListVosList.setLevels("1"/*TODO*/);
                groupUserListVosList.setFullName(userInfosList.getUsername());
                groupUserListVosList.setPassword(userInfosList.getPassword());
                groupUserListVosList.setVip(userInfosList.getDemo());//TODO
            });
        });
        ZzGroup zzGroup = this.zzGroupDao.queryById(id);
        if (zzGroup ==null) throw new RuntimeException("未查询到群组记录");

        List<GroupUserListVo> resultList = this.orderByGroupUser(groupUserListVos, zzGroup.getCreator());

        pageInfoGroupInfo.setList(resultList);
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
        return this.zzGroupDao.groupUserListTotal(id);
    }

    @Override
    public List<ZzGroup> queryGroupListByUserId(String id) throws Exception {
        return this.zzGroupDao.queryGroupListByUserId(id);
    }

    private List<GroupUserListVo> orderByGroupUser(List<GroupUserListVo> groupUserListVos,String creator) throws RuntimeException{
        List<GroupUserListVo> resultList = groupUserListVos.stream()
                .filter(listf -> !listf.getUserId().equals(creator)&&listf.getVip()!=0)
                .sorted((a, b) -> a.getVip() - b.getVip())
                .collect(Collectors.toList());
        try {
            resultList.add(0,groupUserListVos.stream()
                    .filter(listf -> listf.getUserId().equals(creator))
                    .collect(Collectors.toList())
                    .get(0));
        } catch (Exception e) {
            throw new RuntimeException("人员排序时未找到创建人");
        }
        resultList.addAll(groupUserListVos.stream()
                .filter(listf -> !listf.getUserId().equals(creator) && listf.getVip() != 0)
                .collect(Collectors.toList()));
        return resultList;
    }
}
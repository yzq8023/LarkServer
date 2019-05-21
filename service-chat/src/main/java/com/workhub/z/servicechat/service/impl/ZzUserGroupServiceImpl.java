package com.workhub.z.servicechat.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupListVo;
import com.workhub.z.servicechat.VO.GroupUserListVo;
import com.workhub.z.servicechat.VO.UserNewMsgVo;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.dao.ZzUserGroupDao;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户群组映射表(ZzUserGroup)表服务实现类
 *
 * @author 忠
 * @since 2019-05-10 14:22:54
 */
@Service("zzUserGroupService")
public class ZzUserGroupServiceImpl implements ZzUserGroupService {
    @Resource
    private ZzUserGroupDao zzUserGroupDao;

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
    public Integer insert(ZzUserGroup zzUserGroup) {
        int insert = this.zzUserGroupDao.insert(zzUserGroup);
        return insert;
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
}
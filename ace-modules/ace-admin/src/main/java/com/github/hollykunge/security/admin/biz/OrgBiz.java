package com.github.hollykunge.security.admin.biz;


import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.entity.Org;
import com.github.hollykunge.security.admin.mapper.MenuMapper;
import com.github.hollykunge.security.admin.mapper.OrgMapper;
import com.github.hollykunge.security.admin.mapper.ResourceRoleMapMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.admin.vo.OrgUsers;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgBiz extends BaseBiz<OrgMapper, Org> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourceRoleMapMapper resourceRoleMapMapper;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 变更组织所分配用户
     *
     * @param orgId
     * @param users
     */
    @CacheClear(pre = "permission")
    public void modifyOrgUsers(int orgId, String users) {

        // TODO: 根据组织id删除用户 mapper.deleteUsersByOrgId(orgId)

        if (!StringUtils.isEmpty(users)) {
            String[] mem = users.split(",");
            for (String m : mem) {
             // TODO: 根据组织id添加用户   mapper.insertUsersByOrgId(orgId, Integer.parseInt(m));
            }
        }
    }

    /**
     * 获取组织关联用户
     *
     * @param orgId
     * @return
     */
    public OrgUsers getOrgUsers(Integer orgId) {
        return new OrgUsers(userMapper.selectUserByOrgId(orgId));
    }

    @Override
    protected String getPageName() {
        return null;
    }
}

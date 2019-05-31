package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.entity.Org;
import com.github.hollykunge.security.admin.entity.OrgUserMap;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.mapper.OrgMapper;
import com.github.hollykunge.security.admin.mapper.OrgUserMapMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.util.EntityUtils;
import com.github.hollykunge.security.common.util.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.plugin.util.UIUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 云雀小组
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgBiz extends BaseBiz<OrgMapper, Org> {

    @Resource
    private OrgUserMapMapper orgUserMapMapper;
    @Resource
    private UserMapper userMapper;

    public List<AdminUser> getOrgUsers(String orgId) {
        List<User> users = userMapper.selectUsersByOrgId(orgId);
        List<AdminUser> userList = new ArrayList<AdminUser>();
        AdminUser au = new AdminUser();
        for (User user : users) {
//            BeanUtils.copyProperties(userMapper.selectOne(user), au);
            //TODO:adminUser实体类中和user对应不上，其中包含name（看着像user表中的operatorName），头像
            BeanUtils.copyProperties(user, au);
            userList.add(au);
        }
        return userList;
    }

    @CacheClear(pre = "permission")
    public void modifyOrgUsers(String orgId, String users) {
        OrgUserMap orgUserMap = new OrgUserMap();
        orgUserMap.setOrgId(orgId);
        orgUserMapMapper.delete(orgUserMap);
        if (!StringUtils.isEmpty(users)) {
            String[] mem = users.split(",");
            for (String m : mem) {
                orgUserMap.setUserId(m);
                EntityUtils.setCreatAndUpdatInfo(orgUserMap);
                orgUserMapMapper.insertSelective(orgUserMap);
            }
        }
    }

    @Override
    protected String getPageName() {
        return null;
    }
}
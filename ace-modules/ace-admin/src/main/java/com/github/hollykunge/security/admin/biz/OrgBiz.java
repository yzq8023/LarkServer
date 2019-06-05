package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.CacheClear;
import com.alibaba.fastjson.JSON;
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
import tk.mybatis.mapper.entity.Example;

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

    public List<AdminUser> getOrgUsers(String orgCode) {
        User userParams = new User();
        userParams.setOrgCode(orgCode);
        Example userExample = new Example(User.class);
        userExample.createCriteria().andLike("orgCode","%"+orgCode+"%");
        List<User> users = userMapper.selectByExample(userExample);
        List<AdminUser> userList;
        userList = JSON.parseArray(JSON.toJSONString(users),AdminUser.class);
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
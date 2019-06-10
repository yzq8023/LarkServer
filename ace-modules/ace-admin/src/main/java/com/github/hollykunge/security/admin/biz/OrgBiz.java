package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.admin.entity.Org;
import com.github.hollykunge.security.admin.entity.OrgUserMap;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.mapper.OrgMapper;
import com.github.hollykunge.security.admin.mapper.OrgUserMapMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.OrgUser;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.util.EntityUtils;
import com.github.hollykunge.security.common.vo.TreeNode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    @Resource
    private UserBiz userBiz;

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
    @Cache(key = "orgUsers{2}")
    public List<OrgUser> getOrg(List<Org> orgs, String parentTreeId) {
        return this.buildByRecursive(orgs, parentTreeId);
    }
    private List<OrgUser> buildByRecursive(List<Org> orgs,Object root) {
        List<OrgUser> result = new ArrayList<>();
        List<OrgUser> treeNodes ;
        treeNodes = JSON.parseArray(JSON.toJSONString(orgs),OrgUser.class);
        for (int i = 0; i < treeNodes.size(); i++) {
            OrgUser treeNode = treeNodes.get(i);
            treeNode.setTitle(orgs.get(i).getOrgName());
            if (root.equals(treeNode.getParentId())) {
                OrgUser children = findChildren(treeNode, treeNodes);
                result.add(children);
            }
        }
        return result;
    }
    private OrgUser  findChildren(OrgUser treeNode, List<OrgUser> treeNodes) {
        for (OrgUser it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren()==null) {
                    treeNode.setChildren(new ArrayList<TreeNode>());
                }
                OrgUser children = findChildren(it, treeNodes);
                //如果children中的children为空则为底层子节点，赋值用户信息
                if(children.getChildren().size()==0){
                    User params = new User();
                    params.setOrgCode(super.selectById(children.getId()).getOrgCode());
                    List<User> users = userBiz.selectList(params);
                    users.stream().forEach(user ->{
                        OrgUser orgUser = new OrgUser();
                        BeanUtils.copyProperties(user,orgUser);
                        orgUser.setScopedSlotsTitle("userNode");
                        orgUser.setTitle(user.getName());
                        orgUser.setOnline(true);
                        children.add(orgUser);
                    });
                }
                children.setScopedSlotsTitle("orgNode");
                children.setOnline(null);
                //todo:iocn数据库中没有字段
                treeNode.add(children);

            }
        }
        return treeNode;
    }
}
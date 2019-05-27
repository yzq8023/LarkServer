package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.Cache;
import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.*;
import com.github.hollykunge.security.admin.mapper.*;
import com.github.hollykunge.security.admin.vo.ActionEntitySet;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.MenuTree;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.exception.BaseException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 协同设计小组
 * @create 2017-06-12 8:48
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleBiz extends BaseBiz<RoleMapper, Role> {

    @Resource
    private ResourceRoleMapMapper resourceRoleMapMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleUserMapMapper roleUserMapMapper;

    @Override
    protected String getPageName() {
        return null;
    }

    @Cache(key = "admin:getUsers")
    public List<AdminUser> getRoleUsers(int roleId) {
        List<AdminUser> resultData = new ArrayList<>();
        List<User> usersByOrgId = userMapper.selectUsersByRoleId(roleId + "");
        List<User> users = Collections.synchronizedList(usersByOrgId);
        users.parallelStream().forEach(user -> {
            AdminUser frontUser = new AdminUser();
            BeanUtils.copyProperties(user, frontUser);
            resultData.add(frontUser);
        });
        return resultData;
    }

    @CacheClear(pre = "permission")
    public void modifyRoleUsers(int roleId, String users) {
        int deleteCount = userMapper.deleteUsersByRoleId(roleId + "");
        if (deleteCount < 0) {
            throw new BaseException("系统异常错误...");
        }
        if (!StringUtils.isEmpty(users)) {
            String[] mem = users.split(",");
            for (String m : mem) {
                RoleUserMap roleUserMapDo = new RoleUserMap();
                roleUserMapDo.setRoleId(roleId + "");
                roleUserMapDo.setUserId(m);
                roleUserMapMapper.insert(roleUserMapDo);
            }
        }
    }

    @CacheClear(keys = {"permission:menu", "permission:u"})
    public void modifyAuthorityMenu(int roleId, String[] menus) {
        int deleteCount = resourceRoleMapMapper.deleteByAuthorityIdAndResourceType(roleId + "", AdminCommonConstant.RESOURCE_TYPE_MENU);
        if (deleteCount < 0) {
            throw new BaseException("系统异常错误...");
        }
        List<Menu> menuList = menuMapper.selectAll();
        Map<String, String> map = new HashMap<String, String>(256);
        for (Menu menu : menuList) {
            map.put(menu.getId(), menu.getParentId());
        }
        Set<String> relationMenus = new HashSet<String>();
        relationMenus.addAll(Arrays.asList(menus));
        ResourceRoleMap authority = null;
        for (String menuId : menus) {
            findParentID(map, relationMenus, menuId);
        }
        for (String menuId : relationMenus) {
            authority = new ResourceRoleMap();
            authority.setRoleId(roleId + "");
            authority.setResourceId(menuId);
            resourceRoleMapMapper.insertSelective(authority);
        }
    }

    private void findParentID(Map<String, String> map, Set<String> relationMenus, String id) {
        String parentId = map.get(id);
        if (String.valueOf(AdminCommonConstant.ROOT).equals(id)) {
            return;
        }
        relationMenus.add(parentId);
        findParentID(map, relationMenus, parentId);
    }

    public List<MenuTree> getAuthorityMenu(int roleId) {
        List<Menu> menus = menuMapper.selectMenuByRoleId(roleId, AdminCommonConstant.RESOURCE_TYPE_MENU);
        List<MenuTree> trees = new ArrayList<>();
        MenuTree node = null;
        for (Menu menu : menus) {
            node = new MenuTree();
            node.setTitle(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees;
    }

    /**
     * 通过角色id获取菜单的element
     * ps：该方法获取了角色的element，也就获取了element的对应的菜单
     *
     * @param roleId 角色id
     */
    @Cache(key = "admin:getElementByMenu")
    public List<ActionEntitySet> getElement(String roleId) {
        return resourceRoleMapMapper.getElement(roleId, AdminCommonConstant.RESOURCE_TYPE_BTN);
    }

    //    @CacheClear(keys = {"permission:ele", "permission:u"})
//    public void modifyAuthorityElement(int roleId, int menuId, int elementId) {
//        //TODO: 数据库那感觉有些问题添加element时少menuId
//        ResourceRoleMap authority = new ResourceRoleMap();
//        authority.setRoleId(roleId + "");
//        authority.setResourceId(elementId + "");
//        resourceRoleMapMapper.insertSelective(authority);
//    }
//
//    @CacheClear(keys = {"permission:ele", "permission:u"})
//    public void removeAuthorityElement(int roleId, int elementId) {
//        ResourceRoleMap authority = new ResourceRoleMap();
//        authority.setRoleId(roleId + "");
//        authority.setResourceId(elementId + "");
//        resourceRoleMapMapper.delete(authority);
//    }

}

package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.ResourceRoleMap;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.entity.Menu;

import com.github.hollykunge.security.admin.mapper.RoleMapper;
import com.github.hollykunge.security.admin.mapper.MenuMapper;
import com.github.hollykunge.security.admin.mapper.ResourceRoleMapMapper;

import com.github.hollykunge.security.admin.vo.AuthorityMenuTree;

import com.github.hollykunge.security.admin.vo.UserRole;
import com.github.hollykunge.security.api.vo.authority.PermissionInfo;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-12 8:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleBiz extends BaseBiz<RoleMapper, Role> {

    @Autowired
    private ResourceRoleMapMapper resourceRoleMapMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void insertSelective(Role entity) {
        super.insertSelective(entity);
    }

    @Override
    public void updateById(Role entity) {
        super.updateById(entity);
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 获取群组关联用户
     *
     * @param groupId
     * @return
     */
    public RoleUsers getRoleUsers(int groupId) {
        return new RoleUsers(userMapper.selectMemberByRoleId(groupId), userMapper.selectLeaderByRoleId(groupId));
    }

    /**
     * 变更群主所分配用户
     *
     * @param groupId
     * @param members
     * @param leaders
     */
    @CacheClear(pre = "permission")
    public void modifyRoleUsers(int groupId, String members, String leaders) {
        mapper.deleteRoleLeadersById(groupId);
        mapper.deleteRoleMembersById(groupId);
        if (!StringUtils.isEmpty(members)) {
            String[] mem = members.split(",");
            for (String m : mem) {
                mapper.insertRoleMembersById(groupId, Integer.parseInt(m));
            }
        }
        if (!StringUtils.isEmpty(leaders)) {
            String[] mem = leaders.split(",");
            for (String m : mem) {
                mapper.insertRoleLeadersById(groupId, Integer.parseInt(m));
            }
        }
    }

    /**
     * 变更群组关联的菜单
     *
     * @param groupId
     * @param menus
     */
    @CacheClear(keys = {"permission:menu","permission:u"})

    public void modifyAuthorityMenu(String roleId, String[] menus) {
        // TODO: 根据角色id和资源类型删除资源 resourceRoleMapMapper.deleteByAuthorityIdAndResourceType(roleId + "", AdminCommonConstant.RESOURCE_TYPE_MENU)
        List<Menu> menuList = menuMapper.selectAll();
        Map<String, String> map = new HashMap<String, String>();
        for (Menu menu : menuList) {
            map.put(menu.getId().toString(), menu.getParentId().toString());
        }
        Set<String> relationMenus = new HashSet<String>();
        relationMenus.addAll(Arrays.asList(menus));
        ResourceRoleMap authority = null;
        for (String menuId : menus) {
            findParentID(map, relationMenus, menuId);
        }
        for (String menuId : relationMenus) {

            authority = new ResourceRoleMap();
            authority.setRoleId(roleId);
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

    /**
     * 分配资源权限
     *
     * @param roleId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele","permission:u"})
    public void modifyAuthorityElement(String roleId, String elementId) {
        ResourceRoleMap authority = new ResourceRoleMap();
        authority.setRoleId(roleId);
        authority.setResourceId(elementId);
        resourceRoleMapMapper.insertSelective(authority);
    }

    /**
     * 移除资源权限
     *
     * @param roleId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele","permission:u"})
    public void removeAuthorityElement(String roleId, String elementId) {
        ResourceRoleMap authority = new ResourceRoleMap();
        authority.setRoleId(roleId);
        authority.setResourceId(elementId);
        resourceRoleMapMapper.delete(authority);
    }


    /**
     * 获取群主关联的菜单
     *
     * @param groupId
     * @return
     */
    public List<AuthorityMenuTree> getAuthorityMenu(String roleId) {
        // TODO: 根据角色id和类型获取菜单列表
        List<Menu> menus = menuMapper.selectMenuByRoleId(roleId, AdminCommonConstant.RESOURCE_TYPE_MENU);
        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
        AuthorityMenuTree node = null;
        for (Menu menu : menus) {
            node = new AuthorityMenuTree();
            node.setText(menu.getTitle());
            BeanUtils.copyProperties(menu, node);
            trees.add(node);
        }
        return trees;
    }

    /**
     * 获取群组关联的资源
     *
     * @param groupId
     * @return
     */
    public List<Integer> getAuthorityElement(String roleId) {
        ResourceRoleMap authority = new ResourceRoleMap();
        authority.setRoleId(roleId);
        List<ResourceRoleMap> authorities = resourceRoleMapMapper.select(authority);
        List<Integer> ids = new ArrayList<Integer>();
        for (ResourceRoleMap auth : authorities) {
            ids.add(Integer.parseInt(auth.getResourceId()));
        }
        return ids;
    }

    /**
     * 获取所有的角色
     * @return
     */
    public List<UserRole> getAllSystemRole(){
        List<UserRole> result = new ArrayList<UserRole>();
        List<Role> allSystemRole = mapper.getAllSystemRole();
        List<Role> roles = Collections.synchronizedList(allSystemRole);
        allSystemRole.parallelStream().forEach(role -> {
            String roleId = role.getId();
            List<Menu> systemRoleMenu = mapper.getSystemRoleMenu(roleId);
            Collections.synchronizedList(systemRoleMenu);
        });
//        for (Role info : allSystemRole) {
//            boolean anyMatch = allSystemRole.parallelStream().anyMatch(new Predicate<Role>() {
//                @Override
//                public boolean test(Role role) {
//                    return role.getCode().equals(info.getCode());
//                }
//            });
//            if (anyMatch) {
//                current = info;
//                break;
//            }
//        }

        return null;
    }

}

package com.github.hollykunge.security.admin.biz;

import com.ace.cache.annotation.CacheClear;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.ResourceRoleMap;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.entity.Menu;
import com.github.hollykunge.security.admin.entity.ResourceAuthority;
import com.github.hollykunge.security.admin.mapper.RoleMapper;
import com.github.hollykunge.security.admin.mapper.MenuMapper;
import com.github.hollykunge.security.admin.mapper.ResourceRoleMapMapper;
import com.github.hollykunge.security.admin.mapper.UserMapper;
import com.github.hollykunge.security.admin.vo.AuthorityMenuTree;
import com.github.hollykunge.security.admin.vo.RoleUsers;
import com.github.hollykunge.security.common.biz.BaseBiz;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

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
    private UserMapper userMapper;
    @Autowired
    private ResourceRoleMapMapper resourceRoleMapMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void insertSelective(Role entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Role parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.insertSelective(entity);
    }

    @Override
    public void updateById(Role entity) {
        if (AdminCommonConstant.ROOT == entity.getParentId()) {
            entity.setPath("/" + entity.getCode());
        } else {
            Role parent = this.selectById(entity.getParentId());
            entity.setPath(parent.getPath() + "/" + entity.getCode());
        }
        super.updateById(entity);
    }

    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 变更角色用户
     *
     * @param roleId
     * @param users
     */
    @CacheClear(pre = "permission")
    public void modifyRoleUsers(int roleId, String users) {
        // TODO: 此处添加根据角色id删除用户 mapper.deleteUsersByRoleId(groupId)
        if (!StringUtils.isEmpty(users)) {
            String[] mem = users.split(",");
            for (String m : mem) {
                //TODO: 此处添加根据角色Id添加用户 mapper.insertUsersByRoleId(roleId, Integer.parseInt(m))
            }
        }
    }

    /**
     * 变更角色关联的导航菜单
     *
     * @param roleId
     * @param menus
     */
    @CacheClear(keys = {"permission:menu","permission:u"})
    public void modifyAuthorityMenu(int roleId, String[] menus) {
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
            authority.setRoleid(roleId);
            authority.setResourceid(menuId);
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
     * 变更角色关联的权限资源
     *
     * @param roleId
     * @param menuId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele","permission:u"})
    public void modifyAuthorityElement(int roleId, int menuId, int elementId) {
        ResourceRoleMap authority = new ResourceRoleMap(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setRoleid(roleId);
        authority.setResourceid(elementId);
        resourceRoleMapMapper.insertSelective(authority);
    }

    /**
     * 移除资源权限
     *
     * @param roleId
     * @param menuId
     * @param elementId
     */
    @CacheClear(keys = {"permission:ele","permission:u"})
    public void removeAuthorityElement(int roleId, int menuId, int elementId) {
        ResourceRoleMap authority = new ResourceRoleMap();
        authority.setRoleid(roleId);
        authority.setResourceid(elementId);
        resourceRoleMapMapper.delete(authority);
    }

    /**
     * 根据用户id获取角色
     */
    @CacheClear
    public Role getRoleByUserId(String userId){
        //TODO: 根据用户id查询角色信息 return mapper.selectRoleByUserId(userId)
        return null;
    }


    /**
     * 获取角色关联的菜单
     *
     * @param roleId
     * @return
     */
    public List<AuthorityMenuTree> getAuthorityMenu(int roleId) {
        // TODO: 根据角色id和类型获取菜单列表
        List<Menu> menus = menuMapper.selectMenuByAuthorityId(String.valueOf(roleId), AdminCommonConstant.AUTHORITY_TYPE_GROUP);
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
     * 获取角色关联的资源
     *
     * @param roleId
     * @return
     */
    public List<Integer> getAuthorityElement(int roleId) {
        ResourceRoleMap authority = new ResourceRoleMap(AdminCommonConstant.AUTHORITY_TYPE_GROUP, AdminCommonConstant.RESOURCE_TYPE_BTN);
        authority.setRoleId(roleId);
        List<ResourceRoleMap> authorities = resourceRoleMapMapper.select(authority);
        List<Integer> ids = new ArrayList<Integer>();
        for (ResourceRoleMap auth : authorities) {
            ids.add(Integer.parseInt(auth.getResourceId()));
        }
        return ids;
    }
}

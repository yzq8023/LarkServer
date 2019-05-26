package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.admin.biz.RoleBiz;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.MenuTree;
import com.github.hollykunge.security.admin.vo.RoleTree;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.util.TreeUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 协同设计小组
 * @create 2017-06-12 8:49
 */

@Controller
@RequestMapping("admin")
@Api("角色模块")
public class RoleController extends BaseController<RoleBiz, Role> {

    /**
     * 根据角色获取用户
     *
     * @param id 角色id
     */
    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AdminUser>> getUsers(@PathVariable int id) {
        return new ObjectRestResponse<List<AdminUser>>().data(baseBiz.getRoleUsers(id)).rel(true);
    }

    /**
     * 批量修改角色用户
     *
     * @param id    角色id
     * @param users 用户ids
     */
    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable int id, String users) {
        baseBiz.modifyRoleUsers(id, users);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 批量修改角色菜单，TODO：参数形式？
     *
     * @param id        角色id
     * @param menuTrees 以逗号分隔的菜单
     */
    @RequestMapping(value = "/{id}/menu", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthority(@PathVariable int id, String menuTrees) {
        String[] menus = menuTrees.split(",");
        baseBiz.modifyAuthorityMenu(id, menus);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 获取菜单和关联功能
     *
     * @param id 角色id
     */
    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<MenuTree>> getMenuAuthority(@PathVariable int id) {
        return new ObjectRestResponse().data(baseBiz.getAuthorityMenu(id)).rel(true);
    }

    /**
     * TODO：这是干嘛的?
     */
    @PostMapping("/element")
    public ListRestResponse getElement(@RequestParam("roleId") int roleId) {
        return new ListRestResponse("", 0, baseBiz.getElement(roleId + ""));
    }

    /**
     * 获取角色树
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<RoleTree> tree(String name) {
        Role role = new Role();
        role.setName(name);
        return getTree(baseBiz.selectList(role), AdminCommonConstant.ROOT);
    }

    private List<RoleTree> getTree(List<Role> roles, String root) {
        List<RoleTree> trees = new ArrayList<>();
        RoleTree node = null;
        for (Role role : roles) {
            node = new RoleTree();
            node.setLabel(role.getName());
            BeanUtils.copyProperties(role, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
}

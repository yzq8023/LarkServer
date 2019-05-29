package com.github.hollykunge.security.admin.rest;

import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.admin.biz.RoleBiz;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.vo.AdminPermission;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.RoleTree;
import com.github.hollykunge.security.admin.vo.UserRole;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.util.TreeUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
    public ObjectRestResponse<List<AdminUser>> getUsers(@PathVariable String id) {
        return new ObjectRestResponse<List<AdminUser>>().data(baseBiz.getRoleUsers(Integer.parseInt(id))).rel(true);
    }

    /**
     * 批量修改角色用户
     *
     * @param id    角色id
     * @param users 用户ids
     */
    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable String id, @RequestParam("users") String users) {
        baseBiz.modifyRoleUsers(id, users);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 批量修改角色菜单，TODO：参数形式？
     *
     * @param id        角色id
     * @param permissionList 被勾选的权限Element和Element所对应的菜单信息
     */
    @RequestMapping(value = "/{id}/menu", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthority(@PathVariable String id, @RequestBody List<AdminPermission> permissionList) {
        baseBiz.modifyAuthorityMenu(id, permissionList);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 获取菜单和关联功能
     *
     * @param id 角色id
     */
    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AdminPermission>> getMenuAuthority(@PathVariable String id) {
        return new ObjectRestResponse().data(baseBiz.getAuthorityMenu(id)).rel(true);
    }


    /**
     * 获取角色树
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<RoleTree> tree(@RequestParam("parentTreeId") String parentTreeId) {
        if(StringUtils.isEmpty(parentTreeId)){
            parentTreeId = AdminCommonConstant.ROOT;
        }
        return getTree(baseBiz.selectListAll(), parentTreeId);
    }

    private List<RoleTree> getTree(List<Role> roles, String root) {
        List<RoleTree> trees = new ArrayList<>();
        RoleTree node = null;
        for (Role role : roles) {
            node = new RoleTree();
            node.setLabel(role.getName());
            String jsonNode = JSON.toJSONString(role);
            node = JSON.parseObject(jsonNode,RoleTree.class);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
}

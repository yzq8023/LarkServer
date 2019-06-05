package com.github.hollykunge.security.admin.rest;

import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.admin.biz.RoleBiz;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.vo.AdminPermission;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.RoleTree;
import com.github.hollykunge.security.admin.vo.UserRole;
import com.github.hollykunge.security.common.msg.ListRestResponse;
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
import java.util.Map;

/**
 * @author 协同设计小组
 * @create 2017-06-12 8:49
 */

@Controller
@RequestMapping("role")
@Api("角色模块")
public class RoleController extends BaseController<RoleBiz, Role> {
    /**
     * 根据用户id获取用户角色接口
     * @param userId 用户id
     * @return 角色
     */
    @RequestMapping(value = "/userRole", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<Role>> getUserRoles(@RequestParam("id") String userId) {
        List<Role> roleList = baseBiz.getRoleByUserId(userId);
        return new ListRestResponse<>("",roleList.size(),roleList);
    }

    /**
     * 根据角色获取用户
     *
     * @param id 角色id
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<AdminUser>> getUsers(@RequestParam("id") String id) {
        List<AdminUser> roleUsers = baseBiz.getRoleUsers(Integer.parseInt(id));
        return new ListRestResponse<>("",roleUsers.size(),roleUsers);
    }

    /**
     * 批量修改角色用户
     *
     * @param id    角色id
     * @param users 用户ids
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@RequestParam("id") String id, @RequestParam("users") String users) {
        baseBiz.modifyRoleUsers(id, users);
        return new ObjectRestResponse().rel(true).msg("修改成功");
    }

    /**
     * 批量修改角色菜单
     * @param permissionMap  包含 角色id和权限list
     */
    @RequestMapping(value = "/permission", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthority(@RequestBody Map<String,Object> permissionMap) {
        String id =(String)permissionMap.get("id");
        List<AdminPermission> permissionList;
        JSON.toJSONString(permissionMap.get("permissionList"));
        permissionList = JSON.parseArray(JSON.toJSONString(permissionMap.get("permissionList")),AdminPermission.class);
        baseBiz.modifyAuthorityMenu(id, permissionList);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 获取菜单和关联功能
     *
     * @param id 角色id
     */
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<AdminPermission>> getMenuAuthority(@RequestParam("id") String id) {
        List<AdminPermission> authorityMenu = baseBiz.getAuthorityMenu(id);
        return new ListRestResponse("",authorityMenu.size(),authorityMenu);
    }


    /**
     * 获取角色树
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<RoleTree>> tree(@RequestParam("parentTreeId") String parentTreeId) {
        if(StringUtils.isEmpty(parentTreeId)){
            parentTreeId = AdminCommonConstant.ROOT;
        }
        List<RoleTree> tree = getTree(baseBiz.selectListAll(), parentTreeId);
        return new ListRestResponse<>("",tree.size(),tree);
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

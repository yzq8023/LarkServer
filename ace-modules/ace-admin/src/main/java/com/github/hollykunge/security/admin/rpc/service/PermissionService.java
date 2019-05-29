package com.github.hollykunge.security.admin.rpc.service;

import com.github.hollykunge.security.admin.biz.ElementBiz;
import com.github.hollykunge.security.admin.biz.MenuBiz;
import com.github.hollykunge.security.admin.biz.RoleBiz;
import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Element;
import com.github.hollykunge.security.admin.entity.Menu;
import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.vo.*;
import com.github.hollykunge.security.api.vo.authority.PermissionInfo;
import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.hollykunge.security.auth.client.jwt.UserAuthUtil;
import com.github.hollykunge.security.common.constant.CommonConstants;
import com.github.hollykunge.security.common.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 初始化用户权限服务
 * @author 协同设计小组
 * @date 2017/9/12
 */
@Service
public class PermissionService {
    @Autowired
    private RoleBiz roleBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private MenuBiz menuBiz;
    @Autowired
    private ElementBiz elementBiz;
    @Autowired
    private UserAuthUtil userAuthUtil;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public UserInfo getUserByUserId(String userId) {
        UserInfo info = new UserInfo();
        User user = userBiz.getUserByUserId(userId);
        BeanUtils.copyProperties(user, info);
        info.setId(user.getId());
        return info;
    }

    public UserInfo validate(String userId,String password){
        UserInfo info = new UserInfo();
        User user = userBiz.getUserByUserId(userId);
        if (encoder.matches(password, user.getPassword())) {
            BeanUtils.copyProperties(user, info);
            info.setId(user.getId());
        }
        return info;
    }

    public List<FrontPermission> getAllPermission() {
        List<Menu> menus = menuBiz.selectListAll();
        List<FrontPermission> result = new ArrayList<FrontPermission>();
        PermissionInfo info = null;
        menu2permission(menus, result);
        List<Element> elements = elementBiz.selectListAll();
        element2permission(result, elements);
        return result;
    }

    private void menu2permission(List<Menu> menus, List<FrontPermission> result) {
        FrontPermission info;
        for (Menu menu : menus) {
            info = new FrontPermission();
            info.setMenuId(menu.getId());
            info.setTitle(menu.getTitle());
            result.add(info);
        }
    }

    /**
     * 根据userId获取角色所属菜单功能
     * @param userId
     * @return
     */
    public List<FrontPermission> getPermissionByUserId(String userId) {

        List<Menu> menus = menuBiz.getUserAuthorityMenuByUserId(userId + "");
        List<FrontPermission> result = new ArrayList<FrontPermission>();
        menu2permission(menus, result);

        List<Element> elements = elementBiz.getElementByUserId(userId + "");
        List<ActionEntitySet> actionEntitySets = new ArrayList<ActionEntitySet>();
        element2permission(actionEntitySets, elements);


        return result;
    }

    /**
     * 菜单功能-》操作权限
     * @param result
     * @param elements
     */
    private void element2permission(List<ActionEntitySet> result, List<Element> elements) {
        ActionEntitySet info;
        for (Element element : elements) {

            info = new ActionEntitySet();

            info.setDefaultCheck(true);
            info.setDescription(element.getDescription());
            info.setMethod(element.getMethod());

            result.add(info);
        }
    }

    public FrontUser getUserInfo(String token) throws Exception {
        String userId = userAuthUtil.getInfoFromToken(token).getId();
        if (userId == null) {
            return null;
        }
        UserInfo user = this.getUserByUserId(userId);
        FrontUser frontUser = new FrontUser();
        BeanUtils.copyProperties(user, frontUser);

        UserRole userRole = this.getUserRoleByUserId(userId);
        frontUser.setUserRole(userRole);

        return frontUser;
    }


    public UserRole getUserRoleByUserId(String userId) {
        List<Role> roleList = roleBiz.getRoleByUserId(userId);
        UserRole userRole = new UserRole();
        //使用list可能有点不太对劲
        BeanUtils.copyProperties(roleList.get(0), userRole);
        List<FrontPermission> frontPermissionList = this.getPermissionByUserId(userId);
        userRole.setFrontPermissionList(frontPermissionList);
        return userRole;
    }
}
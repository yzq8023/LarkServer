package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.rpc.service.PermissionService;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.FrontUser;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关信息接口
 *
 * @author 协同设计小组
 * @create 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserBiz,User> {
    @Value("${auth.user.token-header}")
    private String headerName;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<?> getUserInfo(String token, HttpServletRequest request) throws Exception {
        if(StringUtils.isEmpty(token)){
            token = request.getHeader(headerName);
        }
        FrontUser userInfo = permissionService.getUserInfo(token);
        return new ObjectRestResponse().data(userInfo).msg("").rel(true);
    }

    /**
     * 给用户设置角色接口
     * @param userId 用户id
     * @param roles 角色集（以“，”隔开的字符串）
     * @return
     */
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse modifyUserRoles(@RequestParam("userId")String userId, @RequestParam("roles")String roles){
        baseBiz.modifyRoles(userId,roles);
        return new ObjectRestResponse().rel(true).msg("");
    }

    /**
     * 添加用户接口
     * ps：由于添加用户的时候需要给用户角色，故而单独提供一个
     * 添加用户的接口，该接口携带返回值
     * @param entity 用户实体
     * @return 用户id
     */
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<AdminUser> addUser(@RequestBody User entity) {
        User user = baseBiz.addUser(entity);
        //未防止暴露密码使用AdminUser携带id返回前端
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(user,adminUser);
        return new ObjectRestResponse<AdminUser>().data(adminUser).rel(true);
    }

//    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
//    public @ResponseBody
//    List<MenuTree> getMenusByUsername(String token) throws Exception {
//        return permissionService.getMenusByUsername(token);
//    }
}

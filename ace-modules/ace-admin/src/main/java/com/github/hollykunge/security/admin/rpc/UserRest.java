package com.github.hollykunge.security.admin.rpc;

import com.ace.cache.annotation.Cache;
import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.rpc.service.PermissionService;
import com.github.hollykunge.security.api.vo.authority.FrontPermission;
import com.github.hollykunge.security.api.vo.user.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-21 8:15
 */
@RestController
@RequestMapping("api")
public class UserRest {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserBiz userBiz;

    @Cache(key="permission")
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public @ResponseBody
    List<FrontPermission> getAllPermission(){
        return permissionService.getAllPermission();
    }

    @RequestMapping(value = "/user/un/{userId}/permissions", method = RequestMethod.GET)
    public @ResponseBody List<FrontPermission> getPermissionByUserId(@PathVariable("userId") String userId){
        return permissionService.getPermissionByUserId(userId);
    }

    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    public @ResponseBody UserInfo validate(String pid,String password){
        return permissionService.validate(pid,password);
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.POST)
    public @ResponseBody UserInfo info(String userId){
        User user = userBiz.getUserByUserId(userId);
        UserInfo info = new UserInfo();

        BeanUtils.copyProperties(user, info);
        info.setId(user.getId());
        return info;
    }

    @RequestMapping(value = "/user/all", method = RequestMethod.POST)
    public @ResponseBody List<User> all(){
        List<User> users = userBiz.getUsers();
//        List<User> infos = new ArrayList<User>();
//        BeanUtils.copyProperties(users, infos);
        return users;
    }
}

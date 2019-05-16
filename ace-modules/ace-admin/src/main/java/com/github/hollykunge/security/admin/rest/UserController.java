package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.rpc.service.PermissionService;
import com.github.hollykunge.security.admin.vo.FrontUser;
import com.github.hollykunge.security.admin.vo.MenuTree;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * 用户相关信息接口
 *
 * @author 协同设计小组
 * @create 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserBiz,User> {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo(String token) throws Exception {
        FrontUser userInfo = permissionService.getUserInfo(token);
        if(userInfo==null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @RequestMapping("addUser")
    @ResponseBody
    public ObjectRestResponse<Boolean> addUser(@RequestBody User entity){
        baseBiz.addUser(entity);
        return new ObjectRestResponse<Boolean>().rel(true);
    }

    @RequestMapping("removeUser")
    @ResponseBody
    public ObjectRestResponse<Boolean> removeUserById(@RequestBody User entity){
        return new ObjectRestResponse<Boolean>().rel(baseBiz.removeUser(entity));
    }

    @RequestMapping("updateUser")
    @ResponseBody
    public ObjectRestResponse<Boolean> updateUserById(@RequestBody User entity){
        return new ObjectRestResponse<Boolean>().rel(baseBiz.updateUser(entity));
    }

//    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
//    public @ResponseBody
//    List<MenuTree> getMenusByUsername(String token) throws Exception {
//        return permissionService.getMenusByUsername(token);
//    }
}

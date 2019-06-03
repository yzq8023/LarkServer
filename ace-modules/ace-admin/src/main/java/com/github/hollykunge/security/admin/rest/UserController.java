package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.admin.rpc.service.PermissionService;
import com.github.hollykunge.security.admin.vo.FrontUser;
import com.github.hollykunge.security.admin.vo.MenuTree;
import com.github.hollykunge.security.common.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Value("${auth.user.token-header}")
    private String headerName;

    @Autowired
    private PermissionService permissionService;
    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo( String token, HttpServletRequest request) throws Exception {
        if(StringUtils.isEmpty(token)){
            token = request.getHeader(headerName);
        }
        FrontUser userInfo = permissionService.getUserInfo(token);
        if(userInfo==null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

//    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
//    public @ResponseBody
//    List<MenuTree> getMenusByUsername(String token) throws Exception {
//        return permissionService.getMenusByUsername(token);
//    }
}

package com.github.hollykunge.security.auth.controller;

import com.github.hollykunge.security.auth.service.AuthService;
import com.github.hollykunge.security.auth.util.user.JwtAuthenticationRequest;
import com.github.hollykunge.security.auth.util.user.JwtAuthenticationResponse;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author holly
 */
@RestController
@RequestMapping("jwt")
public class AuthController {
    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @Value("${auth.user.defaultPassword}")
    private String defaultPassword;

    @RequestMapping(value = "token", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,HttpServletRequest request) throws Exception {
        String dnname = request.getHeader("dnname");
        if(StringUtils.isEmpty(dnname)){
            throw new BaseException("请求头中无身份信息...");
        }
        dnname = new String (dnname.getBytes("iso8859-1"));
        final String token = authService.login(dnname, defaultPassword);
//        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return new ObjectRestResponse().data(new JwtAuthenticationResponse(token)).msg("获取token成功");
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<?> removeAuthenticationToken(HttpServletRequest request) throws Exception {
        return new ObjectRestResponse().data("").msg("注销成功").rel(true);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return new ObjectRestResponse().data(null).msg("刷新token失败...");
        } else {
            return new ObjectRestResponse().data(new JwtAuthenticationResponse(refreshedToken)).msg("刷新token成功...");
        }
    }

    @RequestMapping(value = "verify", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<?> verify(String token) throws Exception {
        authService.validate(token);
        return new ObjectRestResponse<>().rel(true);
    }

    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<?> invalid(String token){
        authService.invalid(token);
        return new ObjectRestResponse().rel(true);
    }
}

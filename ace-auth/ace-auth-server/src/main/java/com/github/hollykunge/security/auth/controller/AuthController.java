package com.github.hollykunge.security.auth.controller;

import com.github.hollykunge.security.auth.service.AuthService;
import com.github.hollykunge.security.auth.util.user.JwtAuthenticationRequest;
import com.github.hollykunge.security.auth.util.user.JwtAuthenticationResponse;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping(value = "token", method = RequestMethod.POST)
    @ResponseBody
    public ListRestResponse<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return new ListRestResponse("",0,new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
//            return ResponseEntity.badRequest().body(null);
            return new ListRestResponse("",0,null);
        } else {
            return new ListRestResponse("",0,new JwtAuthenticationResponse(refreshedToken));
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

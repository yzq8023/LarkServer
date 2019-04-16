package com.workhub.z.servicechat.feign;

import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.github.hollykunge.security.auth.configuration.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ace-admin")
public interface IUserService extends com.github.hollykunge.security.auth.feign.IUserService {
    /**
    *@Description: 根据user身份证查询用户信息
    *@Param: sn
    *@return: UserInfo
    *@Author: 忠
    *@date: 2019/3/22
    */
//    @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
//    public UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
////    @RequestMapping(value = "/api/user/test", method = RequestMethod.POST)
////    public void test();
}

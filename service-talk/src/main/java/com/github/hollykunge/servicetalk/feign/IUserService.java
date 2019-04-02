package com.github.hollykunge.servicetalk.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.hollykunge.security.api.vo.user.UserInfo;
/**
 * ${描述}
 *
 * @author: holly
 * @since: 2019/4/1
 */
@FeignClient(value = "ace-admin")
public interface IUserService {
    @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST)
    public UserInfo validate(@RequestParam("username") String username, @RequestParam("password") String password);
}

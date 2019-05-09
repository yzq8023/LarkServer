package com.github.hollykunge.servicediscuss.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author holly
 */
@FeignClient(value = "ace-auth")
public interface ITokenService {
    /**
     * 验证token并获取userId
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/token/verify", method = RequestMethod.GET)
    public String verify(@RequestParam("token") String token);

}

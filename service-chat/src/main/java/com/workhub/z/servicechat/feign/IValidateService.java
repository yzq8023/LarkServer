package com.workhub.z.servicechat.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
*@Description: token认证类
*@Author: 忠
*@date: 2019/4/11
*/
@FeignClient(value = "ace-auth")
public interface IValidateService {
    void validate(String token) throws Exception;
}

package com.workhub.z.servicechat.controller.group;

import cn.hutool.json.JSONObject;
import com.workhub.z.servicechat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
*@Description: 群组管理类
*@Author: 忠
*@date: 2019/3/21
*/
@Controller
@RequestMapping("/groupController")
public class groupController {

    @Autowired
    private GroupService groupService;

    /**
    *@Description: 查询群组
    *@Param: 用户id
    *@return: 用户群组model
    *@Author: 忠
    *@date: 2019/3/21
    */
    @RequestMapping("/getGroupByUser")
    public Object getGroupByUser(String userId){
        JSONObject json = null;
        groupService.queryGroupByUser(userId);
        return json;
    }
    /**
    *@Description: 关闭群组
    *@Param: 群组id
    *@return: bool
    *@Author: 忠
    *@date: 2019/3/21
    */

    /**
    *@Description: 创建群组
    *@Param: 群组model
    *@return: bool
    *@Author: 忠
    *@date: 2019/3/21
    */

    /**
    *@Description: 退出群组
    *@Param: 群组id,用户id
    *@return: bool
    *@Author: 忠
    *@date: 2019/3/21
    */
}

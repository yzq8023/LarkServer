package com.workhub.z.servicechat.api;

//import com.github.hollykunge.security.api.vo.user.UserInfo;
import com.workhub.z.servicechat.feign.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
*@Description: 系统用户初始化类
*@Author: 忠
*@date: 2019/3/21
*/
@Controller
@RequestMapping("/api/Initialization")
public class Initialization {

    @Autowired
    private IUserService iUserService;

    /**
    *@Description: 查询user所在群组
    *@Param: userid
    *@return: user群组列表
    *@Author: 忠
    *@date: 2019/3/21
    */

    /**
    *@Description: 查询用户详细信息
    *@Param: userid
    *@return: usermodel
    *@Author: 忠
    *@date: 2019/3/21
    */
    @RequestMapping("/getInfo")
    public void queryUserInfoBySn(String sn){
//        iUserService.queryUserinfoBySn(sn);
        iUserService.validate("11","11");
//        return null;
    }

    /**
    *@Description: 最近联系人
    *@Param:userid
    *@return:userlist
    *@Author: 忠
    *@date: 2019/3/21
    */
    @RequestMapping("/getContact")
    public void queryContactById(){
        // TODO: 2019/5/9 跟海南确认详细的结果集
//        {
//            "id": "@id",
//                "name": "@cname",
//                "time": "10:20",
//                "lastMessage": "ganshenmene",
//                "avatar": "/avatar2.jpg",
//                "sender": "王白",
//                "unreadNum": 19,
//                "atMe": false,
//                "isTop": false,
//                "isMute": true,
//                "isGroup": true
//        }
    }
}

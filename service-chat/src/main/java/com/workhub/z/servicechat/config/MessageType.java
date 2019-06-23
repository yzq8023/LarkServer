package com.workhub.z.servicechat.config;

/**
*@Description: 消息码
*@Param:
*@return:
*@Author: 忠
*@date: 2019/5/29
*/
public class MessageType {

    //个人消息
    public static final int PRIVATE_MSG = 0;
    //群消息
    public static final int GROUP_MSG = 1;
    //系统消息
    public static final int SYS_MSG = 2;

    //群创建
    public static final int GROUP_CREATE = 3;
    //群组操作
    public static final int GROUP_EDIT = 4;
    //加入群组
    public static final int GROUP_JOIN_MSG = 5;
    //邀请加入群组
    public static final int GROUP_INVITE_MSG = 6;
    //退出群组
    public static final int GROUP_EXIT_MSG = 7;
    //关闭群组
    public static final int GROUP_CLOSE_MSG = 8;

    //返回码 boolean
    public static final int MSG_EDIT_READ = 9;
    //群组创建成功应答
    public static final int CREATE_GROUP_ANS = 10;
    //队列
    //人员、组织机构变更

    //工作台


}

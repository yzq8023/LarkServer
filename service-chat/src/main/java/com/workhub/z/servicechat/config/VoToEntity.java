package com.workhub.z.servicechat.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.entity.ZzGroupMsg;
import com.workhub.z.servicechat.entity.ZzPrivateMsg;

import static com.workhub.z.servicechat.config.RandomId.getUUID;

/**
*@Description: entity和vo转换
*@Author: 忠
*@date: 2019/5/29
*/
public abstract class VoToEntity {
    // TODO: 2019/5/31 增加转换异常处理
    /**
    *@Description:  Msg转换
    *@Author: 忠
    *@date: 2019/5/29
    */
    public static Object MsgVOToModel(String text) {
        JSONObject json = JSON.parseObject(text);
        ZzPrivateMsg zpm = new ZzPrivateMsg();
        zpm.setMsgId(getUUID());//信息ID
        zpm.setMsgSender(json.getString("fromId"));
        zpm.setMsgReceiver(json.getString("toId"));
        zpm.setLevels(json.getString("secretLevel"));
        zpm.setIsRead("0");
        zpm.setMsgPath("");
        zpm.setIsDelete("0");
        zpm.setMsg(json.getString("content"));
        zpm.setMsgType(json.getString("type"));
        zpm.setSendTime(json.getSqlDate("time"));
        zpm.setReceiverTime(json.getSqlDate("time"));
        return zpm;
    }

    /**
     *@Description:  群Msg转换
     *@Author: 忠
     *@date: 2019/5/29
     */
    public static Object GroupMsgVOToModel(String text) {
        JSONObject json = JSON.parseObject(text);
        ZzGroupMsg zpm = new ZzGroupMsg();
        zpm.setMsgId(getUUID());//信息ID
        zpm.setMsgSender(json.getString("fromId"));
        zpm.setMsgReceiver(json.getString("toId"));
        zpm.setLevels(json.getString("secretLevel"));
        zpm.setMsgPath("");
        zpm.setIsDelete("0");
        zpm.setMsg(json.getString("content"));
        zpm.setMsgType(json.getString("type"));
        zpm.setSendTime(json.getSqlDate("time"));
        zpm.setIsImportant("0");
        return zpm;
    }


}

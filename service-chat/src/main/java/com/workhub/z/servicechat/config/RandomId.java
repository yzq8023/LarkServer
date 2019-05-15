package com.workhub.z.servicechat.config;

import java.util.UUID;
/**
*@Description: 随机ID生成
*@Author: 忠
*@date: 2019/5/15
*/
public class RandomId {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

}

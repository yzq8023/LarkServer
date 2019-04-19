package com.workhub.z.servicechat.config;

import java.util.UUID;

public class RandomId {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

}

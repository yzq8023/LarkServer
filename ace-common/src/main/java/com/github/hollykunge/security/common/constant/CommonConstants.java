package com.github.hollykunge.security.common.constant;

import org.tio.utils.time.Time;

/**
 *
 * @author 协同设计小组
 * @date 2017/8/29
 */
public class CommonConstants {
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40101;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40301;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_NAME = "currentUser";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
    /** 消息交换机的名字*/
    public static final String PORTAL_EXCHANGE = "exchangePortal";
    //门户接口队列
    public static final String PORTAL_QUEUE_NAMA = "portal_queue";

    /** tio用ip数据监控统计，时间段*/
    public static final Long DURATION_1 = Time.MINUTE_1 * 5;
    public static final Long[] IPSTAT_DURATIONS = new Long[]{DURATION_1};

    //密级判断
    public static final Integer EX_LEVELS = 40000;

    public static final String WEB_USERHOST = "userHost";
    public static final String WEB_USERNAME = "userName";
    public static final String WEB_USERID = "userId";
}

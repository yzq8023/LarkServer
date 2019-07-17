package com.github.hollykunge.security.constants;

import org.tio.utils.time.Time;

public interface Constants {
    /**
     * 用于群聊的group id
     */
    String GROUP_ID = "showcase-websocket";
    /**
     * tio用ip数据监控统计，时间段
     */
    Long DURATION_1 = Time.MINUTE_1 * 5;
    Long[] IPSTAT_DURATIONS = new Long[]{DURATION_1};
}

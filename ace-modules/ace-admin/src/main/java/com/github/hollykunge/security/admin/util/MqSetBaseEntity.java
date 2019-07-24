package com.github.hollykunge.security.admin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 使用反射，mq新增数据赋值baseEntity基础数据
 * @author zhhongyu
 * @since 2019-07-23
 */
public class MqSetBaseEntity {
    public static <T> T setCreatData(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnknownHostException {
        Method setCrtTime = t.getClass().getMethod("setCrtTime");
        Method setCrtUser = t.getClass().getMethod("setCrtUser");
        Method setCrtName = t.getClass().getMethod("setCrtName");
        Method setCrtHost = t.getClass().getMethod("setCrtHost");
        setCrtTime.invoke(Date.class,new Date());
        setCrtUser.invoke(String.class,"mq");
        setCrtName.invoke(String.class,"mq");
        setCrtHost.invoke(String.class,InetAddress.getLocalHost().toString());
        return t;
    }

    public static <T> T setUpdate(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnknownHostException {
        Method setCrtTime = t.getClass().getMethod("setUpdTime");
        Method setUpdUser = t.getClass().getMethod("setUpdUser");
        Method setUpdName = t.getClass().getMethod("setUpdName");
        Method setUpdHost = t.getClass().getMethod("setUpdHost");
        setCrtTime.invoke(Date.class,new Date());
        setUpdUser.invoke(String.class,"mq");
        setUpdName.invoke(String.class,"mq");
        setUpdHost.invoke(String.class,InetAddress.getLocalHost().toString());
        return t;
    }
}

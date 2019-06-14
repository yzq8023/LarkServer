package com.workhub.z.servicechat.config;

import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
*@Description: 通用方法
*@Author: 忠
*@date: 2019/5/14
*/
public class common {

    /**
     * 加密解密算法 执行一次加密，两次解密
     * @param inStr 加密字符
     * @return
     */
    public static String convert(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);
    }

    /**
     * 校验文本包含非法字符，返回所包含的非法字符以,分割没有返回空串
     * @param str 文本
     * @param index 非法字符
     * @return
     */
    public static String stringSearch(String str,String... index){
        if(null == str) throw new NullPointerException("str is null");
        if(null == index) throw new NullPointerException("index is null");
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(index).filter(indexStr -> str.contains(indexStr)).forEach(indexResult ->{
                stringBuilder.append(indexResult).append(",");
        });
        String resultStr = stringBuilder.toString();
        if (resultStr.length() == 0) return resultStr;
        return resultStr.substring(0,resultStr.length()-1);
    }

    /**
     * 校验文本包含非法字符，返回所包含的非法字符以,分割没有返回空串
     * @param str 文本
     * @return
     */
    public static String stringSearch(String str){
        if(null == str) throw new NullPointerException("str is null");
        Set<String> setIndex = new HashSet<String>();//TODO 来源
        setIndex.add("机密");
        setIndex.add("非密");
        setIndex.add("密级");
        setIndex.add("秘密");
        StringBuilder stringBuilder = new StringBuilder();
        setIndex.stream().filter(setIndexFilter -> str.contains(setIndexFilter)).forEach(setIndexResult ->{
            stringBuilder.append(setIndexResult).append(",");
        });
        String resultStr = stringBuilder.toString();
        if (resultStr.length() == 0) return resultStr;
        return resultStr.substring(0,resultStr.length()-1);
    }

    /**
     * 校验文本包含非法字符，返回所包含的非法字符以,分割没有返回空串
     * @param str 文本
     * @param setStr 非法字符
     * @return
     */
    public static String stringSearch(String str, Set<String> setStr){
        if(null == str) throw new NullPointerException("str is null");
        if(null == setStr) throw new NullPointerException("setStr is null");
        StringBuilder stringBuilder = new StringBuilder();
        setStr.stream().filter(setIndexFilter -> str.contains(setIndexFilter)).forEach(setIndexResult ->{
            stringBuilder.append(setIndexResult).append(",");
        });
        String resultStr = stringBuilder.toString();
        if (resultStr.length() == 0) return resultStr;
        return resultStr.substring(0,resultStr.length()-1);
    }

    /**
     * 涉密检索
     * @param txt
     * @param zzDictionaryWordsList
     * @return
     */
    public static String stringSearch(String txt,List<ZzDictionaryWords> zzDictionaryWordsList) {
        // TODO: 2019/5/31 获取涉密词汇列表
        if(null == txt) throw new NullPointerException("txt is null");
        if(null == zzDictionaryWordsList||zzDictionaryWordsList.isEmpty()) throw new NullPointerException("zzDictionaryWordsList is null");
        Set<String> strSet = new HashSet<String>();
        Optional<ZzDictionaryWords> max = zzDictionaryWordsList.stream()
                    .filter(setIndexFilter -> txt.contains(setIndexFilter.getWordName()))
                    .max(Comparator.comparing(zz ->Integer.parseInt(zz.getWordCode())));
        try {
            return max.get().getWordCode();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 敏感词替换
     * @param txt
     * @param zzDictionaryWordsList
     * @return
     */
    public static String sensitiveSearch(String txt, List<ZzDictionaryWords> zzDictionaryWordsList) {
        if (null == txt) throw new NullPointerException();
        zzDictionaryWordsList.stream().forEach(list ->{
            txt.replace(list.getWordName(),list.getReplaceWord());
        });
        return txt;
    }
    /**
    *@Description: 判断用户是否在线
    *@Param: 上下文ChannelContext
    *@return: boolean
    *@Author: 忠
    *@date: 2019/5/30
    */
    public static boolean checkUserOnline(ChannelContext channelContext,String userId){
        ChannelContext checkChannelContext =

                Tio.getChannelContextByBsId(channelContext.getGroupContext(),userId);
        //检查是否在线
        boolean isOnline = checkChannelContext != null && !checkChannelContext.isClosed;
        return isOnline;
    }
    /**
     *@Description: 判断msg数据体是否正确
     *@Param: string 接收的消息
     *@return: boolean
     *@Author: 忠
     *@date: 2019/5/30
     */
    public static boolean isJson(String text){
        return false;
    }
    /**
     *@Description: 把entity null 转换成空字符串,时间转换成当前时间，long转换成0L
     *@Param: string 接收的实体
     *@return: boolean
     *@Author: zhuqz
     *@date: 2019/06/10
     */
    public static<T> void putEntityNullToEmptyString (T enity) throws Exception{
        //遍历enity类 成员为String类型 属性为空的全部替换为“”
        Field[] fields = enity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            // 获取属性的名字
            String name = fields[i].getName();
            // 将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            // 获取属性的类型
            String type = fields[i].getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (type.equals("class java.lang.String")) {
                Method m = enity.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                String value = (String) m.invoke(enity);
                //System.out.println("数据类型为：String");
                if (value == null) {
                    //set值
                    Class[] parameterTypes = new Class[1];
                    parameterTypes[0] = fields[i].getType();
                    m = enity.getClass().getMethod("set" + name, parameterTypes);
                    String string = new String("");
                    Object[] objects = new Object[1];
                    objects[0] = string;
                    m.invoke(enity, objects);
                }
            }
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (type.equals("class java.util.Date")) {
                Method m = enity.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                Date value = (Date) m.invoke(enity);
                //System.out.println("数据类型为：String");
                if (value == null) {
                    //set值
                    Class[] parameterTypes = new Class[1];
                    parameterTypes[0] = fields[i].getType();
                    m = enity.getClass().getMethod("set" + name, parameterTypes);
                    Date date = new Date();
                    Object[] objects = new Object[1];
                    objects[0] = date;
                    m.invoke(enity, objects);
                }
            }
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (type.equals("long")) {
                boolean hasFun = true;
                Method m = null;
                try{
                    m = enity.getClass().getMethod("get" + name);
                }catch (NoSuchMethodException e){//可能是序列化的long
                    hasFun=false;
                }
                if(!hasFun){
                    continue;
                }
                // 调用getter方法获取属性值
                Long value = (Long) m.invoke(enity);
                //System.out.println("数据类型为：String");
                if (value == null) {
                    //set值
                    Class[] parameterTypes = new Class[1];
                    parameterTypes[0] = fields[i].getType();
                    m = enity.getClass().getMethod("set" + name, parameterTypes);
                    long data = 0L;
                    Object[] objects = new Object[1];
                    objects[0] = data;
                    m.invoke(enity, objects);
                }
            }
        }
    }
    //获取当前日期
    public static String getCurrentDate(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        return  format.format(calendar.getTime());
    }
    //获取上个月第一天
    public static String getBeforeMonthFirstDay(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return  format.format(calendar.getTime());
    }
}

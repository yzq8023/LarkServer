package com.workhub.z.servicechat.config;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupInfoVO;
import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import java.util.*;
import java.util.stream.Collectors;

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
}

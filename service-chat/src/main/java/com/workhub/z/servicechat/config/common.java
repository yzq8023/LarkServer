package com.workhub.z.servicechat.config;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.comp.Todo;
import com.sun.xml.internal.bind.v2.TODO;
import com.workhub.z.servicechat.VO.GroupInfo;

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

    public static void main(String[] args){
        Page<Object> pageMassage = PageHelper.startPage(1, 10);
        pageMassage.setTotal(100);
        System.out.println(pageMassage);
        PageInfo<GroupInfo> pageInfoGroupInfo = new PageInfo<GroupInfo>();
        pageInfoGroupInfo.setSize(10);
        pageInfoGroupInfo.setPageNum(2);
        List<GroupInfo> list = new ArrayList<GroupInfo>();
        for (int n=0;n<100;n++)list.add(new GroupInfo());
//        pageInfoGroupInfo.setList(list);
        pageInfoGroupInfo.setTotal(10);
        System.out.println(pageInfoGroupInfo);
//        System.out.println(common.stringSearch("阿里巴巴 哥斯拉 弗兰多路 蕾米莉亚 190拿分033奥巴马金正恩机-密密级.密"));
//        System.out.println(common.convert("`0923870348934h2u20!@#$%^&*[]★()))>>PL'"));
//        System.out.println(common.convert(convert("`0923870348934h2u20!@#$%^&*[]★()))>>PL'")));
//        System.out.println(common.convert("陋醸嶀嶀T咑旛抽T彣億奮趛T蔊簇菽仮TEMD抋割DGG夑嶀騘醥欗思李Y宲宲结Z宲"));
    }
}

package com.workhub.z.servicechat.config;

import java.util.HashMap;
import java.util.Map;

//存放数据词典
public class DataDictionary {
    //数据词典涉密类型
    private final static Map<String,String> wordTypeDic=new HashMap<>();
    static {
        wordTypeDic.put("涉密","1");
        wordTypeDic.put("敏感","2");
    }
    //数据字典词汇编码
    private final static Map<String,String> wordCodeDic=new HashMap<>();
    static {
        wordCodeDic.put("秘密","40");
        wordCodeDic.put("机密","60");
    }

    public static Map<String, String> getWordTypeDic() {
        return wordTypeDic;
    }

    public static Map<String, String> getWordCodeDic() {
        return wordCodeDic;
    }
}

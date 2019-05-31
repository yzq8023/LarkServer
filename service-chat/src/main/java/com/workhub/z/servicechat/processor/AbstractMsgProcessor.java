package com.workhub.z.servicechat.processor;

import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.server.IworkServerConfig;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.util.List;

import static com.workhub.z.servicechat.config.common.*;

public class AbstractMsgProcessor {

    @Autowired
    ZzDictionaryWordsService dictionaryWordsService;
    public WsResponse getWsResponse(String msg){
        return WsResponse.fromText(msg, IworkServerConfig.CHARSET);
    }
    // TODO: 2019/5/31 保密，关键词过滤

    public String messageFiltering(String msg){
        // TODO: 2019/5/31  获取数据词典，涉密词汇
        List<ZzDictionaryWords> zzDictionaryWordsList =null;
        return stringSearch(msg,zzDictionaryWordsList);
    }
    // TODO: 2019/5/31 敏感词过滤
    
    public boolean checkUserOnline(ChannelContext channelContext, String userId){
        ChannelContext checkChannelContext =
                Tio.getChannelContextByBsId(channelContext.getGroupContext(),userId);
        //检查是否在线
        boolean isOnline = checkChannelContext != null && !checkChannelContext.isClosed;
        return isOnline;
    }


}

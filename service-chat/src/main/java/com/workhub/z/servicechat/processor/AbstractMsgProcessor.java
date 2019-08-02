package com.workhub.z.servicechat.processor;

import com.workhub.z.servicechat.VO.MsgAnswerVO;
import com.workhub.z.servicechat.entity.ZzDictionaryWords;
import com.workhub.z.servicechat.entity.ZzMessageInfo;
import com.workhub.z.servicechat.entity.ZzMsgReadRelation;
import com.workhub.z.servicechat.server.IworkServerConfig;
import com.workhub.z.servicechat.service.ZzDictionaryWordsService;
import com.workhub.z.servicechat.service.ZzMessageInfoService;
import com.workhub.z.servicechat.service.ZzMsgReadRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.util.Date;
import java.util.List;

import static com.workhub.z.servicechat.config.MessageType.*;
import static com.workhub.z.servicechat.config.RandomId.getUUID;
import static com.workhub.z.servicechat.config.common.*;
@Service
public class AbstractMsgProcessor {

    @Autowired
    ZzDictionaryWordsService dictionaryWordsService;
    @Autowired
    ZzMsgReadRelationService msgReadRelationService;
    @Autowired
    ZzMessageInfoService messageInfoService;

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
                Tio.getChannelContextById(channelContext.getGroupContext(),userId);
        //检查是否在线
        boolean isOnline = checkChannelContext != null && !checkChannelContext.isClosed;
        return isOnline;
    }

    /**
    *@Description: 存储未读消息
    *@Param:
    *@return:
    *@Author: 忠
    *@date: 2019/6/12
    */
    public void saveNoReadMsg(String sender, String receiver){
        ZzMsgReadRelation msgReadRelation = new ZzMsgReadRelation();
        msgReadRelation.setId(getUUID());
        msgReadRelation.setReceiver(receiver);
        msgReadRelation.setSender(sender);
        msgReadRelation.setSendType("1");
        msgReadRelationService.insert(msgReadRelation);
    }

    /**
    *@Description: 清除未读消息
    *@Param:
    *@return:
    *@Author: 忠
    *@date: 2019/6/12
    */
    public void deleteNoReadMsg(String sender, String receiver){
        msgReadRelationService.deleteByConsumerAndSender(sender,receiver);
    }

    /**
    *@Description: 存储消息
    *@Param: sender，receiver，createtime，content，levels,megid
    *@return:
    *@Author: 忠
    *@date: 2019/6/23
    */
    public void saveMessageInfo(String type,String sender, String receiver, String levels, Date createtime, String content, String msgId){
        ZzMessageInfo messageInfo = new ZzMessageInfo();
        messageInfo.setContent(content);
        messageInfo.setCreatetime(createtime);
        messageInfo.setLevels(levels);
        messageInfo.setReceiver(receiver);
        messageInfo.setSender(sender);
        messageInfo.setMsgId(msgId);
        messageInfo.setType(type);
        messageInfoService.insert(messageInfo);
    }

    /**
    *@Description: 应答信息
    *@Param:消息内容
    *@return:
    *@Author: 忠
    *@date: 2019/7/30
    */
    public void msgAnswer(String msg,String nId,ChannelContext channelContext) throws Exception {
        MsgAnswerVO msgAnswerVO = new MsgAnswerVO();
        msgAnswerVO.setCode(MSG_ANSWER);
        msgAnswerVO.setContactId((String)getJsonStringKeyValue(msg,"toId"));
        msgAnswerVO.setnId(nId);
        msgAnswerVO.setoId((String)getJsonStringKeyValue(msg,"id"));
        msgAnswerVO.setStatus(SUCCESS_ANSWER);
        Tio.sendToUser(channelContext.getGroupContext(),(String)getJsonStringKeyValue(msg,"fromId"),this.getWsResponse(msgAnswerVO.toString()));
    }

}

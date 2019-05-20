package com.workhub.z.servicechat.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.workhub.z.servicechat.config.AsyncTaskConfig;
import com.workhub.z.servicechat.config.AsyncTaskService;
import com.workhub.z.servicechat.entity.ZzGroup;
import com.workhub.z.servicechat.feign.IValidateService;
import com.workhub.z.servicechat.service.ZzGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Component
public class IworkWsMsgHandler implements IWsMsgHandler {
    private static Logger log = LoggerFactory.getLogger(IworkWsMsgHandler.class);
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncTaskConfig.class);
    private static AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);

    @Autowired
    protected ZzGroupService groupService;
    protected IValidateService iValidateService;
    private static IworkWsMsgHandler  serverHandler ;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        serverHandler = this;
        serverHandler.groupService = this.groupService;
        serverHandler.iValidateService = this.iValidateService;
        // 初使化时将已静态化的Service实例化
    }

    public static IworkWsMsgHandler me = new IworkWsMsgHandler();

    private IworkWsMsgHandler() {

    }

    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = request.getClientIp();
        String token = request.getParam("token");
        String userid=request.getParam("userid");
//      用户token验证
//        iValidateService.validate(token);
//      前端 参数 绑定信息
        Aio.bindUser(channelContext,userid);
//      加入系统消息组
        Aio.bindGroup(channelContext, Const.GROUP_SYS);
//       根据握手信息，将用户绑定到群组
        // TODO: 2019/5/13 获取当前登录用户所有群组
//        List<ZzGroup> listGroupModel =  serverHandler.groupService.queryById("11");
//        for (int i = 0; i < listGroupModel.size() ; i++) {
//            String groupid  =listGroupModel.get(i).getGroupId();
//            Aio.bindGroup(channelContext,groupid);
//            System.out.println("join group "+ listGroupModel.get(i).getGroupName());
//        }

        log.info("收到来自{}的ws握手包\r\n{}", clientip, request.toString());
        return httpResponse;
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Aio.remove(channelContext, "receive close flag");
        return
                null;
    }

    /*
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();//获取websocket握手包
        String name=httpRequest.getParam("name");
        String userid=httpRequest.getParam("id");
        String type=httpRequest.getParam("type");
        String r=httpRequest.getParam("recipient");


        JSONObject jsonObject = JSONObject.parseObject(text);
        String code = jsonObject.getString("code");
        String message = jsonObject.getString("data");
        JSONObject jsonObject2 = JSONObject.parseObject(message);
        JSONArray content = jsonObject2.getJSONArray("content");
//        for(int i=0;i<content.size();i++){
//            String s = content.getString(i);
//            JSONObject data = JSONObject.fromObject(s);
//            System.out.println(data2.getString("address"));
//            System.out.println(data2.getString("province"));
//            System.out.println(data2.getString("district"));
//            System.out.println(data2.getString("city"));
//        }

//        利用多线程进行数据处理
//        解析Code
        switch (Integer.parseInt(code)){
            case Const.JOIN_GROUP:
                asyncTaskService.joinGroup();
                break;
            case Const.EXIT_GROUP:
                asyncTaskService.exitGroup();
                break;
            case Const.PING:
                break;
            case Const.SEND_MSG:
                asyncTaskService.saveMessage();
                break;
            case Const.CLOSE_GROUP:
                asyncTaskService.closeGroup();
                break;
        }

//      Aio.bindUser(channelContext,userid);
//      Aio.bindGroup(channelContext, Const.GROUP_ID);

        //获取前端消息 展示
        if (log.isDebugEnabled()) {
            log.debug("握手包:{}", httpRequest);
        }

        log.info("收到ws消息:{}", text);
        String t = String.valueOf(text);
//        JSONObject jsonObject = JSON.parseObject(text);

        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        System.out.println(t);
//      String msg = channelContext.getClientNode().toString() + " 说：" + text;
        String msg = t;
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, IworkServerConfig.CHARSET);
        //群发
        Aio.bSendToGroup(channelContext.getGroupContext(), Const.GROUP_SYS, wsResponse);
        //系统消息
//      Aio.sendToAll(channelContext.getGroupContext(),wsResponse);
//      Aio.sendToUser(channelContext.getGroupContext(),"123",wsResponse);
        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }

}
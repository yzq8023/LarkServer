package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.github.hollykunge.security.common.vo.rpcvo.DiscussVO;
import com.github.hollykunge.security.common.vo.rpcvo.TaskVO;
import com.github.hollykunge.security.common.websocket.tio.baseConfig.CurrentWsServerStarter;
import com.github.hollykunge.security.config.ProduceSenderConfig;
import com.github.hollykunge.security.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.tio.core.Tio;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsResponse;

@RestController
@RequestMapping("/api/portal")
@RefreshScope
public class PortalIndexController {
    @Value("${rpc}")
    private String invokeAgs;
    @Autowired
    @Lazy
    private CurrentWsServerStarter currentWsServerStarter;

    @Autowired
    @Lazy
    private ProduceSenderConfig produceSenderConfig;

    @Value("${websocket.tio.server.charset}")
    private String charset;

    @GetMapping("/getMessage")
    public ObjectRestResponse<String> getMessage(){
            System.out.printf(invokeAgs+"");
            return new ObjectRestResponse<String>().data(invokeAgs);
    }
    @GetMapping("/produceSendMsg/{id}")
    public ObjectRestResponse<String> produceSendMsg(@PathVariable int id){
        if(id == 0){
            DiscussVO discussVO = new DiscussVO();
            discussVO.setId(123+"");
            discussVO.setUser("zhhongyu");
            produceSenderConfig.send(UUIDUtils.generateShortUuid(),discussVO);
        }else if(id == 1){
            TaskVO taskVO = new TaskVO();
            taskVO.setId(123+"");
            taskVO.setColor("红色");
            produceSenderConfig.send(UUIDUtils.generateShortUuid(),taskVO);
        }
        return new ObjectRestResponse<String>().data("success");
    }
    @GetMapping("/sent")
    public ObjectRestResponse<Boolean> send(@RequestParam("msg") String msg){
        ServerGroupContext context = currentWsServerStarter.getServerGroupContext();
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, charset);
        //群发
        Tio.sendToGroup(context,Constants.GROUP_ID,wsResponse);
        return new ObjectRestResponse<Boolean>().data(true);
    }

}

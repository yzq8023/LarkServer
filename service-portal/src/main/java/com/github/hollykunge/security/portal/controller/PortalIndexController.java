package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.github.hollykunge.security.common.vo.rpcvo.DiscussVO;
import com.github.hollykunge.security.common.vo.rpcvo.TaskVO;
import com.github.hollykunge.security.config.ProduceSenderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portal")
//@RefreshScope
public class PortalIndexController {
//    @Value("${rpc}")
//    private String invokeAgs;

    @Autowired
    @Lazy
    private ProduceSenderConfig produceSenderConfig;

//    @PostMapping("/getMessage")
//    public ObjectRestResponse<String> getMessage(){
//            System.out.printf(invokeAgs+"");
//            return new ObjectRestResponse<String>().data(invokeAgs);
//    }
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

}

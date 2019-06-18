package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.CardInfo;
import com.github.hollykunge.security.entity.HeatMap;
import com.github.hollykunge.security.portal.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设置卡片 - 卡片接口
 * @author zhhongyu
 * @since 2019-06-11
 */
@RestController
@RequestMapping("card")
public class CardInfoController extends BaseController<CardService, CardInfo> {

    @GetMapping("/heatmap")
    public ListRestResponse<HeatMap> getHeatMap(@RequestParam("userId") String userId){
return null;
    }
}

package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.HeatMap;
import com.github.hollykunge.security.portal.service.HotMapService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作热力图接口
 * @author zhhongyu
 * @since 2019-06-19
 */
@RestController
@RequestMapping("hotmap")
public class HotMapController extends BaseController<HotMapService, HeatMap> {
    /**
     * 根据userid获取工作热力图接口
     * @return
     */
    @Override
    @GetMapping("/all")
    @ResponseBody
    public ListRestResponse<List<HeatMap>> all(){
        String userID = request.getHeader("userId");
        if(StringUtils.isEmpty(userID)){
            throw new BaseException("request contains no user...");
        }
        HeatMap heatMap = new HeatMap();
        heatMap.setUserId(userID);
        List<HeatMap> heatMaps = baseBiz.selectList(heatMap);
        return new ListRestResponse<>("",heatMaps.size(),heatMaps);
    }
}

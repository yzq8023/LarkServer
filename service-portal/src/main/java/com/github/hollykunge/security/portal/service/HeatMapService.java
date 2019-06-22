package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.entity.HeatMap;
import com.github.hollykunge.security.mapper.HeatMapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 工作热力图业务实现
 *
 * @author zhhongyu
 * @since 2019-06-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HeatMapService extends BaseBiz<HeatMapMapper, HeatMap> {
    @Override
    protected String getPageName() {
        return null;
    }

    public List<HeatMap> getHeatMap(List<HeatMap> heatMapList) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date oneYearAgo = c.getTime();
        Date today = new Date();
        List<HeatMap> heatMaps = new ArrayList<>();
        try {
            heatMaps = getHeatMapData(ft.format(today), ft.format(oneYearAgo), heatMapList);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return heatMaps;
    }

    /**
     * @description: 日期类工具
     * @author: dd
     * @since: 2019-06-21
     * <p>
     * "date": "2017-05-03",
     * "commits": 1,
     * "month": 4,
     * "day": 3,
     * "week": "0"
     */
    private List<HeatMap> getHeatMapData(String dBegin, String dEnd, List<HeatMap> heatMapList) throws ParseException {
        //日期工具类准备
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //设置开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dBegin));

        //设置结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dEnd));

        //装返回的日期集合容器
        List<HeatMap> heatMaps = new ArrayList<HeatMap>();

        // 每次循环给calBegin日期加一天，直到calBegin.getTime()时间等于dEnd
        while (format.parse(dEnd).after(calBegin.getTime())) {

            HeatMap heatMap = new HeatMap();
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            for (HeatMap heatMap1 : heatMapList) {
                if (heatMap1.equals(calBegin)) {
                    heatMap.setCommits(heatMap1.getCommits());
                }
            }
            heatMap.setMapDate(calBegin.getTime());
            heatMap.setDay(calBegin.DATE);
            heatMap.setMonth(calBegin.MONTH + 1);
            heatMap.setWeek(calBegin.DAY_OF_WEEK - 1);

            heatMaps.add(heatMap);
        }
        return heatMaps;
    }
}

package com.github.hollykunge.security.gate.filter;

import com.github.hollykunge.security.api.vo.authority.FrontPermission;
import com.github.hollykunge.security.api.vo.log.LogInfo;
import com.github.hollykunge.security.auth.common.util.jwt.IJWTInfo;
import com.github.hollykunge.security.common.constant.CommonConstants;
import com.github.hollykunge.security.common.context.BaseContextHandler;
import com.github.hollykunge.security.common.util.ClientUtil;
import com.github.hollykunge.security.gate.feign.ILogService;
import com.github.hollykunge.security.gate.utils.DBLog;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.tio.http.common.HttpConst;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class ClearFilter extends ZuulFilter {
    @Autowired
    @Lazy
    private ILogService logService;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        int responseStatusCode = ctx.getResponseStatusCode();
        log.info("responseStatusCode: " + responseStatusCode);
        String host = ClientUtil.getClientIp(ctx.getRequest());
        FrontPermission pm = (FrontPermission) BaseContextHandler.get("pm");
        IJWTInfo user = (IJWTInfo) BaseContextHandler.get("user");
        String isSuccess = "1";
        if (responseStatusCode != CommonConstants.HTTP_SUCCESS) {
            isSuccess = "0";
        }
        if (pm != null && user != null) {
            LogInfo logInfo = new LogInfo(pm.getTitle(), ctx.getRequest().getMethod(), pm.getUri(), new Date(), user.getId(), user.getName(), host, isSuccess,user.getUniqueName());
            DBLog.getInstance().setLogService(logService).offerQueue(logInfo);
        }
        BaseContextHandler.remove();
        return null;
    }

}

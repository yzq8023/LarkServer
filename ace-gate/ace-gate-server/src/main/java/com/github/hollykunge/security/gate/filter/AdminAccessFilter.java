package com.github.hollykunge.security.gate.filter;

import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.api.vo.authority.FrontPermission;
import com.github.hollykunge.security.api.vo.log.LogInfo;
import com.github.hollykunge.security.auth.client.config.ServiceAuthConfig;
import com.github.hollykunge.security.auth.client.config.UserAuthConfig;
import com.github.hollykunge.security.auth.client.jwt.ServiceAuthUtil;
import com.github.hollykunge.security.auth.client.jwt.UserAuthUtil;
import com.github.hollykunge.security.auth.common.util.jwt.IJWTInfo;
import com.github.hollykunge.security.common.context.BaseContextHandler;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.msg.auth.TokenErrorResponse;
import com.github.hollykunge.security.common.msg.auth.TokenForbiddenResponse;
import com.github.hollykunge.security.common.util.ClientUtil;
import com.github.hollykunge.security.gate.feign.ILogService;
import com.github.hollykunge.security.gate.feign.IUserService;
import com.github.hollykunge.security.gate.utils.DBLog;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 网关核心权限拦截类
 *
 * @author 协同设计小组
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class AdminAccessFilter extends ZuulFilter {
    @Autowired
    @Lazy
    private IUserService userService;
    @Autowired
    @Lazy
    private ILogService logService;

    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Value("${zuul.prefix}")
    private String zuulPrefix;
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;

    @Autowired
    private UserAuthConfig userAuthConfig;

    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    @Override
    public String filterType() {
        return "pre";
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
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        BaseContextHandler.setToken(null);
        // 不进行拦截的地址
        if (isStartWith(requestUri)) {
            return null;
        }
        IJWTInfo user = null;
        try {
            user = getJWTUser(request, ctx);
        } catch (Exception e) {
            setFailedRequest(JSON.toJSONString(new TokenErrorResponse(e.getMessage())), 200);
            return null;
        }
        //获取所有的资源信息，包括menu和element
//        List<FrontPermission> permissionIfs = userService.getAllPermissionInfo();
//        // 判断当前资源是否属于权限资源
//        Stream<FrontPermission> stream = getPermissionIfs(requestUri, method, permissionIfs);
//        List<FrontPermission> result = stream.collect(Collectors.toList());
//        FrontPermission[] permissions = result.toArray(new FrontPermission[]{});

//        if (permissions.length > 0) {
//            //判断用户是否有当前资源访问权限
//            checkUserPermission(permissions, ctx, user);
//        }
        //根据用户id获取资源列表，包括菜单和菜单功能
        List<FrontPermission> permissionInfos = userService.getPermissionByUserId(user.getId());
        if(permissionInfos.size()>0){
            checkUserPermission(requestUri,permissionInfos, ctx, user);
        }
        // 申请客户端密钥头，加到header里传递到下方服务
        ctx.addZuulRequestHeader(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        return null;
    }

    /**
     * 获取目标权限资源
     * 请求资源和权限列表匹配，并且与资源方法相同
     * @param requestUri
     * @param method
     * @param serviceInfo
     * @return
     */
    private Stream<FrontPermission> getPermissionIfs(final String requestUri, final String method, List<FrontPermission> serviceInfo) {
        return serviceInfo.stream().filter(new Predicate<FrontPermission>() {
            @Override
            public boolean test(FrontPermission permissionInfo) {
                String uriTemp = permissionInfo.getUri();
                String uri = uriTemp.replaceAll("\\{\\*\\}", "[a-zA-Z\\\\d]+");
                String regEx = "^" + uri + "$";
                return Pattern.compile(regEx).matcher(requestUri).find() && method.equals(permissionInfo.getMethods());
            }
        });
    }

    /**
     * 在上下文中设置当前用户信息和操作日志
     */
    private void setCurrentUserInfoAndLog(RequestContext ctx, IJWTInfo user, FrontPermission pm) {
        String host = ClientUtil.getClientIp(ctx.getRequest());
        ctx.addZuulRequestHeader("userId", user.getId());
        ctx.addZuulRequestHeader("userName", URLEncoder.encode(user.getName()));
        ctx.addZuulRequestHeader("userHost", ClientUtil.getClientIp(ctx.getRequest()));
        LogInfo logInfo = new LogInfo(pm.getMenuId(), pm.getTitle(), pm.getUri(), new Date(), user.getId(), user.getName(), host);
        DBLog.getInstance().setLogService(logService).offerQueue(logInfo);
    }

    /**
     * 返回token中的用户信息
     *
     * @param request
     * @param ctx
     * @return
     */
    private IJWTInfo getJWTUser(HttpServletRequest request, RequestContext ctx) throws Exception {
        String authToken = request.getHeader(userAuthConfig.getTokenHeader());
        if (StringUtils.isBlank(authToken)) {
            authToken = request.getParameter("token");
        }
        ctx.addZuulRequestHeader(userAuthConfig.getTokenHeader(), authToken);
        BaseContextHandler.setToken(authToken);
        return userAuthUtil.getInfoFromToken(authToken);
    }


    /**
     * 检查用户是否有该权限
     * @param permissions
     * @param ctx
     * @param user
     */
    private void checkUserPermission(FrontPermission[] permissions, RequestContext ctx, IJWTInfo user) {
        //根据用户id获取资源列表，包括菜单和菜单功能
        List<FrontPermission> permissionInfos = userService.getPermissionByUserId(user.getId());
        FrontPermission current = null;
        for (FrontPermission info : permissions) {
            boolean anyMatch = permissionInfos.parallelStream().anyMatch(new Predicate<FrontPermission>() {
                @Override
                public boolean test(FrontPermission permissionInfo) {
                    return permissionInfo.getMenuId().equals(info.getMenuId());
                }
            });
            if (anyMatch) {
                current = info;
                break;
            }
        }
        if (current == null) {
            setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("Token Forbidden!")), 200);
        } else {
            setCurrentUserInfoAndLog(ctx, user, current);
//            if (!RequestMethod.GET.toString().equals(current.get)) {
//                setCurrentUserInfoAndLog(ctx, user, current);
//            }
        }
    }


    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 网关抛异常
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(String body, int code) {
        log.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.getResponse().setContentType("text/json;charset=UTF-8");
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * 优化查询该请求资源是否在用户所拥有的权限中
     * @param ctx
     * @param user
     */
    private void checkUserPermission(String requestUri,List<FrontPermission> permissionInfos, RequestContext ctx, IJWTInfo user) {
        if(StringUtils.isEmpty(requestUri)){
            throw new BaseException("requestUri 参数异常...");
        }
        permissionInfos =  permissionInfos.parallelStream()
                .filter(new Predicate<FrontPermission>() {
                    @Override
                    public boolean test(FrontPermission permissionInfo) {
                        if(StringUtils.isEmpty(permissionInfo.getUri())){
                            return false;
                        }
                        return requestUri.contains(permissionInfo.getUri());
                    }
                }).collect(Collectors.toList());

        if(permissionInfos.size()==0){
            setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("Token Forbidden!request url no permission...")), 200);
        }
        boolean anyMatch =
                permissionInfos.parallelStream()
                .anyMatch(new Predicate<FrontPermission>() {
                    @Override
                    public boolean test(FrontPermission permissionInfo) {
                        return permissionInfo.getActionEntitySetList().stream().anyMatch(actionEntitySet ->
                                ctx.getRequest().getMethod().equals(actionEntitySet.getMethod()));
                    }
                });
        if (anyMatch) {
            //该用户有访问路径权限
            setCurrentUserInfoAndLog(ctx, user, permissionInfos.get(0));
        } else {
            setFailedRequest(JSON.toJSONString(new TokenForbiddenResponse("Token Forbidden!request method no permission...")), 200);
        }
    }

}

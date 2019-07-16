package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.common.util.ExportExcelUtils;
import com.github.hollykunge.security.common.util.Query;
import com.github.pagehelper.PageHelper;
import com.github.hollykunge.security.admin.biz.GateLogBiz;
import com.github.hollykunge.security.admin.entity.GateLog;
import com.github.hollykunge.security.admin.entity.User;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.hollykunge.security.admin.constant.AdminCommonConstant.*;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-07-01 20:32
 */
@Controller
@RequestMapping("gateLog")
public class GateLogController extends BaseController<GateLogBiz,GateLog> {
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    @Override
    public TableResultResponse<GateLog> page(@RequestParam Map<String, Object> params){
        String userName = null;
        try {
            userName = URLDecoder.decode(request.getHeader("userName"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> userlist = new ArrayList();
        userlist.add(SYSTEM_USER);
        userlist.add(LOG_USER);
        switch (userName){
            case LOG_USER:
                params.put("crtName",userlist);
                return baseBiz.selectByQueryM( new Query(params),"log");
            case SECURITY_USER:
                params.put("crtName",userlist);
                return baseBiz.selectByQueryM( new Query(params),"Security");
            default:
                return baseBiz.selectByQuery( new Query(params));
        }

    }
    @GetMapping("/export")
    public void  download(HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("日志文件.xls", "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        List<GateLog> gateLogs = baseBiz.selectListAll();
        String columName = "菜单,功能,主机ip,是否成功,访问路径,访问时间,访问人员姓名,身份证号";
        String columCode = "menu,opt,crtHost,isSuccess,uri,crtTime,crtName,pid";
        String sheetName = "日志详情";
        byte[] export = ExportExcelUtils.export(gateLogs, columName, columCode, sheetName, outputStream);
        IOUtils.write(export, outputStream);
    }
}

package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.Notice;
import com.github.hollykunge.security.portal.service.NoticeService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 工作台-公告
 * @author: dd
 * @since: 2019-06-08
 */
@RestController
@RequestMapping("notice")
public class NoticeController extends BaseController<NoticeService, Notice>{
    @RequestMapping(value = "/orgNotice", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<Notice>> orgNotices(@RequestParam String orgCode) {
        String userSecretLevel = request.getHeader("userSecretLevel");
        if(StringUtils.isEmpty(userSecretLevel)){
            throw new BaseException("该用户无密级...");
        }
        Notice notice = new Notice();
        notice.setOrgCode(orgCode);
        List<Notice> notices = baseBiz.selectList(notice,userSecretLevel);
        return new ListRestResponse("",notices.size(),notices);
    }
}

package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.FeedBack;
import com.github.hollykunge.security.portal.service.FeedBackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问题反馈
 * @author hollykunge
 */
@RestController
@RequestMapping("feedback")
public class FeedBackController extends BaseController<FeedBackService, FeedBack> {
}

package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.CommonTools;
import com.github.hollykunge.security.portal.service.CommonToolsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 常用工具增删改接口
 * @author zhhongyu
 */
@RestController
@RequestMapping("commonTools")
public class CommonToolsController extends BaseController<CommonToolsService, CommonTools> {
}

package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.CardInfo;
import com.github.hollykunge.security.portal.service.CardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设置卡片 - 卡片接口
 * @author zhhongyu
 * @since 2019-06-11
 */
@RestController
@RequestMapping("card")
public class CardInfoController extends BaseController<CardService, CardInfo> {
}

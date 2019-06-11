package com.github.hollykunge.security.portal.controller;

import com.github.hollykunge.security.common.context.BaseContextHandler;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.entity.UserCard;
import com.github.hollykunge.security.portal.service.UserCardService;
import com.github.hollykunge.security.vo.UserCardVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户卡片接口
 * @author zhhongyu
 * @since 2019-06-11
 */
@RestController
@RequestMapping("userCard")
public class UserCardController extends BaseController<UserCardService, UserCard> {
    /**
     * 给用户设置卡片接口
     * @param userCard 卡片实体类
     * @return
     */
    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<UserCard> add(@RequestBody UserCard userCard) {
       if(baseBiz.selectCount(userCard) > 0){
           return new ObjectRestResponse<>().rel(false).msg("设置卡片失败，已经添加过了...");
       }
        return super.add(userCard).rel(true).msg("设置成功...");
    }

    /**
     * 用户设置卡片不显示接口
     * @param userCard 卡片实体类
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Boolean> modifyUserCard(@RequestBody UserCard userCard) {
        if(baseBiz.selectCount(userCard) == 0){
           return new ObjectRestResponse<>().msg("用户不显示该卡片...");
        }
        String userID = BaseContextHandler.getUserID();
        userCard.setUserId(userID);
        baseBiz.delete(userCard);
        return new ObjectRestResponse<>().msg("移除成功..").rel(true);
    }

    /**
     * 获取用户要展示的卡片
     * @return
     */
    @RequestMapping(value = "/showCards", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<UserCardVO>> userCards() {
        String userID = BaseContextHandler.getUserID();
        List<UserCardVO> userCardVOS = baseBiz.userCards(userID);
        return new ListRestResponse<>("",userCardVOS.size(),userCardVOS);
    }
}

package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.context.BaseContextHandler;
import com.github.hollykunge.security.entity.CardInfo;
import com.github.hollykunge.security.entity.UserCard;
import com.github.hollykunge.security.mapper.UserCardMapper;
import com.github.hollykunge.security.vo.UserCardVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户卡片业务层
 * @author zhhongyu
 * @since 2019-06-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserCardService extends BaseBiz<UserCardMapper, UserCard> {
    @Resource
    private CardService cardService;
    @Value("${portal.card.w}")
    private int cardWith;

    @Value("${portal.card.h}")
    private int cardHeight;


    @Override
    protected String getPageName() {
        return null;
    }

    @Override
    public void insertSelective(UserCard entity) {
        String userId = BaseContextHandler.getUserID();
        //用userId查询已经添加卡片的数量
        UserCard userParams = new UserCard();
        userParams.setUserId(userId);
        int count = mapper.selectCount(userParams);
        Map<String, Object> coordinate = this.sumCoordinate(count);
        entity.setX((int)coordinate.get("x"));
        entity.setY((int)coordinate.get("y"));
        entity.setW((int)coordinate.get("w"));
        entity.setH((int)coordinate.get("h"));
        entity.setI((String)coordinate.get("i"));
        entity.setUserId(userId);
        super.insertSelective(entity);
    }

    /**
     * 计算坐标的算法，固定的width和heigjt
     * @param count
     * @return
     */
    private Map<String,Object> sumCoordinate(int count){
        int x;
        int y;
        int w = this.cardWith;
        int h = this.cardHeight;
        if((count+1) % 2 == 0){
            x = w;
            y = h*(count-1)/2;
        }else {
            x = 0;
            y = (count/2)*h;
        }
        int i = count++;
        Map<String,Object> result = new HashMap<>();
        result.put("x",x);
        result.put("y",y);
        result.put("w",w);
        result.put("h",h);
        result.put("i",i+"");
        return result;
    }

    /**
     * 根据用户id获取要显示
     * @param userId
     * @return
     */
    public List<UserCardVO> userCards(String userId){
        List<UserCardVO> result = new ArrayList<>();
        UserCard userCard = new UserCard();
        userCard.setUserId(userId);
        List<UserCard> userCards = mapper.select(userCard);
        userCards.parallelStream().forEach(userCardEntity ->{
            UserCardVO userCardVO = new UserCardVO();
            CardInfo card = cardService.selectById(userCardEntity.getCardId());
            BeanUtils.copyProperties(userCardEntity,userCardVO);
            userCardVO.setTitle(card.getTitle());
            userCardVO.setUrl(card.getUrl());
            userCardVO.setId(card.getId());
            result.add(userCardVO);
        });
        return result;
    }
}

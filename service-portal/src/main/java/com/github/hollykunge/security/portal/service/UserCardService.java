package com.github.hollykunge.security.portal.service;

import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.context.BaseContextHandler;
import com.github.hollykunge.security.common.util.UUIDUtils;
import com.github.hollykunge.security.entity.CardInfo;
import com.github.hollykunge.security.entity.UserCard;
import com.github.hollykunge.security.mapper.UserCardMapper;
import com.github.hollykunge.security.vo.UserCardVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    @Autowired
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
        //用userId查询已经添加卡片的数量
        UserCard userParams = new UserCard();
        userParams.setUserId(entity.getUserId());
        entity.setId(UUIDUtils.generateShortUuid());
        entity.setStatus("1");
        mapper.insertSelective(entity);
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
        userCards.stream().forEach(userCardEntity ->{
            UserCardVO userCardVO = new UserCardVO();
            CardInfo card = cardService.selectById(userCardEntity.getCardId());
            BeanUtils.copyProperties(userCardEntity,userCardVO);
            if(card != null){
                userCardVO.setTitle(card.getTitle());
                userCardVO.setUrl(card.getUrl());
                userCardVO.setId(card.getId());
            }
            result.add(userCardVO);
        });
        return result;
    }
}

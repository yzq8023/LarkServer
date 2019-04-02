package com.github.hollykunge.servicediscuss.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.hollykunge.servicediscuss.user.entity.ImGroup;
import com.github.hollykunge.servicediscuss.user.entity.ImUserFriend;

import java.util.List;

public interface IImUserFriendService extends IService<ImUserFriend> {
    /**
     * 根据用户的ID 获取 用户好友(双向用户关系)
     *
     * @param userId 用户ID
     * @return 好友分组的列表
     */
    List<ImGroup> getUserFriends(String userId);
}

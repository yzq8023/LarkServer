package com.workhub.z.servicechat.service.imp;

import com.workhub.z.servicechat.config.PageObject;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.mapper.MessageMapper;
import com.workhub.z.servicechat.model.*;
import com.workhub.z.servicechat.service.MessageService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.Map.Entry;

/**
 * 消息处理service
 * 
 * @author hanxu
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

	private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageMapper messageMapper;

	@Override
	public List<?> queryLatelyMsg(String userId, String chatUserId, String chatType) {
		return this.queryMoreMsg(userId, chatUserId, 0, chatType);
	}

	@Override
	public List<?> queryAllHisGroupMsg(String msg, String groupId) {
		return messageMapper.queryAllHisGroupMsg(msg, groupId);
	}

	@Override
	public void readMsg(String msgId, String userId, String chatId, String type) {
		if (type.equals("system")) {// 系统消息
			// 暂时不需要实现
		} else if (type.equals("group")) {// 讨论组消息
			messageMapper.updateEndTime(msgId, userId, chatId);
		} else {// 私聊消息
			messageMapper.updateIsRead(chatId, userId);
		}
	}

	@Override
	public Map<String, String> notReadCount(String type, String userId) {
		List<Map<String, Object>> list = null;
		if (type.equals("lately")) {// 最近联系人列表中未读消息数量
			list = messageMapper.latelyNotRead(userId);
		} else if (type.equals("group")) {// 讨论组列表未读消息
			list = messageMapper.groupNotRead(userId);
		} else {// 私聊消息未读消息
			list = messageMapper.userNotRead(userId);
		}

		Map<String, String> countMap = new HashMap<>();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				countMap.put(String.valueOf(map.get("ID")), String.valueOf(map.get("COUNT")));
			}
		}
		return countMap;
	}

	@Override
	public int notReadCountZ(String userId, String senderid) {
		List<Map<String, Object>> listGroup = null;
		List<Map<String, Object>> listPra = null;


		listGroup = messageMapper.groupNotRead(userId);

		listPra = messageMapper.userNotRead(userId);


		Map<String, String> countMap = new HashMap<>();
		if (listGroup != null && listGroup.size() > 0) {
			for (Map<String, Object> map : listGroup) {
				countMap.put(String.valueOf(map.get("ID")), String.valueOf(map.get("COUNT")));
			}
		}
		if (listPra != null && listPra.size() > 0) {
			for (Map<String, Object> map : listPra) {
				countMap.put(String.valueOf(map.get("ID")), String.valueOf(map.get("COUNT")));
			}
		}
//		System.out.println(countMap.get(senderid));
		if (countMap.get(senderid)==null){
			return 0;
		}
		else {
			return Integer.parseInt(countMap.get(senderid));}
	}

	@Override
	public List<?> queryMoreMsg(String userId, String chatUserId, int start, String chatType) {
		return this.queryMoreMsg(userId, chatUserId, 0, chatType);
	}

	@Override
	public PageObject queryHiMsg(String content, String chatType, String chatId, String userId, int start, int row, Date startDate, Date endDate) {
			PageObject pageObject = new PageObject();
			if (start < 1) {
				start = 0;
			}
			if (row < 1) {
				row = 10;
			}

			if (chatType.equals("group")) {
				pageObject.setObjectList(messageMapper.groupHiMsg(content, chatId, start, row, startDate, endDate));
				pageObject.setTotal(messageMapper.groupHiMsgCount(content, chatId, startDate, endDate));
			} else if (chatType.equals("user")) {
				pageObject.setObjectList(
						messageMapper.privateHiMsg(content, chatId, userId, start, row, startDate, endDate));
				pageObject.setTotal(messageMapper.privateHiMsgCount(content, chatId, userId, startDate, endDate));
			}

			return pageObject;
	}

	@Override
	public String sendMsgToUser(String userId, String receiverUserId, String content, String msgType, String chatType) {
		// 保存消息到数据库
		Map<String, Object> param = new HashMap<>();

		String msgId = RandomId.getUUID();
		Date sendTime = new Date();
		int count = 0;
		if (chatType.equals("group")) {
			count = messageMapper.saveGroupMsg(msgId, userId, receiverUserId, sendTime, content, msgType);
		} else if (chatType.equals("user")) {
			count = messageMapper.savePrivateMsg(msgId, userId, receiverUserId, sendTime, content, msgType);
		}
		return null;
	}

	@Override
	public void saveSystemNotification(String sender, String title, String type, String content, String isRead, String receiver) {
		messageMapper.saveSystemNotification(RandomId.getUUID(), sender, title, type, content, isRead, receiver);
	}

	@Override
	public PageObject querySystemNotification(String isRead, Date startTime, Date endTime, String title, Integer page, String userId) {
		page = page < 1 ? 1 : page;
		Integer row = 10;

		PageObject pageObject = new PageObject();
		pageObject.setPage(page);
		pageObject.setObjectList(messageMapper.querySystemNotification(isRead,startTime, endTime, title, (row * (page - 1)+1), page*row));
		pageObject.setTotal(messageMapper.querySystemNotificationCount(isRead,startTime, endTime, title, userId));
		return pageObject;
	}

	@Override
	public int queryPrivateUserMessage(PrivateMsgModel privateMsgModel) {
		return messageMapper.queryPrivateUserMessage(privateMsgModel);
	}

	@Override
	public List<ATModel> getAtUser(String groupId, String userId) {
		return messageMapper.getAtUser(groupId, userId);
	}

	@Override
	public void addAtUser(ATModel atm) {
		messageMapper.addAtUser(atm);
	}

	@Override
	public void delAtUser(String groupId, String userId) {
		messageMapper.delAtUser(groupId, userId);
	}

	@Override
	public PageObject queryHisMsgs(String chatUserId, String chatType, int page, int rowStart) {
		page = page < 1 ? 1 : page;
		Integer row = 10;
		PageObject pageObject = new PageObject();
		pageObject.setPage(page);
		if (chatType.equals("group")) {
			pageObject.setObjectList(messageMapper.queryGroupHisMsgs((rowStart > 10 ? rowStart + 1 : 1), page*row, chatUserId));
			pageObject.setTotal(messageMapper.queryGroupHisMsgsCount(chatUserId));
		} else {
			pageObject.setObjectList(messageMapper.queryPrivateHisMsgs((rowStart > 10 ? rowStart + 1 : 1), page*row, chatUserId));
			pageObject.setTotal(messageMapper.queryPrivateHisMsgsCount(chatUserId));
		}
		return pageObject;
	}

	@Override
	public List<?> loadMsg(String chatUserId, String chatType, int page, Integer rowStart) {
		page = page < 1 ? 1 : page;
		Integer row = 10;
		if (chatType.equals("group")) {
			List<GroupMsgModel> groupMsgModel = messageMapper.queryGroupHisMsgs(rowStart + 1, page*row, chatUserId);
			return groupMsgModel;
		} else {
			List<PrivateMsgModel> privateMsgModel = messageMapper.queryPrivateHisMsgs(rowStart + 1, page*row, chatUserId);
			return privateMsgModel;
		}
	}
}

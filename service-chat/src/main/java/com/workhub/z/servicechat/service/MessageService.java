package com.workhub.z.servicechat.service;

import com.workhub.z.servicechat.config.PageObject;
import com.workhub.z.servicechat.model.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MessageService {

	/**
	 * 获得最近聊天信息
	 * 
	 * @param userId
	 *            :用户id
	 * @param chatUserId
	 *            ：聊天对象id
	 * @param chatType
	 *            :聊天对象类型（user,group,system）
	 * @return
	 */
	List<?> queryLatelyMsg(String userId, String chatUserId, String chatType);
	
	/**
	 * 获取全部组信息
	 * @param msg
	 * @param groupId
	 * @return
	 */
	List<?> queryAllHisGroupMsg(String msg, String groupId);

	/**
	 * 标记消息为已读
	 * 
	 * @param msgId
	 *            :消息id
	 * @param userId
	 *            ：用户id
	 * @param chatId
	 *            ：聊天对象id
	 * @param type
	 *            ：聊天类型
	 */
	void readMsg(String msgId, String userId, String chatId, String type);

	/**
	 * 获得最近联系人的未读消息
	 * 
	 * @param type
	 *            ：列表类型
	 * @param userId
	 *            ：用户id
	 * @return
	 */
	Map<String, String> notReadCount(String type, String userId);
	/**
	 * 获得最近联系人的未读消息-忠
	 *
	 * @param senderid
	 *            ：发送者
	 * @param userId
	 *            ：用户id
	 * @return
	 */
	int notReadCountZ(String userId, String senderid);

	/**
	 * 获得最近聊天信息
	 *
	 * @param userId
	 *            ：用户id
	 * @param chatUserId
	 *            ：聊天对象id
	 * @param start
	 *            ：加载更多消息的起始位置
	 * @param chatType
	 *            ：聊天对象类型（user,group,system）
	 * @return
	 */
	List<?> queryMoreMsg(String userId, String chatUserId, int start, String chatType);

	/**
	 * 查询历史消息记录
	 *
	 * @param content
	 *            ：消息内容
	 * @param chatType
	 *            ：聊天类型
	 * @param chatId
	 *            ：聊天对象
	 * @param userId
	 *            ：当前用户id
	 * @param start
	 *            ：开始位置（翻页起始）
	 * @param row
	 *            ：显示行数
	 * @param startDate
	 *            ：开始时间
	 * @param endDate
	 *            ：结束时间
	 * @return
	 */
	PageObject queryHiMsg(String content, String chatType, String chatId, String userId, int start, int row,
						  Date startDate, Date endDate);

	/**
	 * 向指定用户、讨论组、系统发送消息
	 * 
	 * @param userId
	 *            ：发送者id
	 * @param receiverUserId
	 *            ：接收者id
	 * @param content
	 *            ：消息内容
	 * @param chatType
	 *            ：聊天类型（system， group， user）
	 * @param msgType
	 *            ：消息类型（text,image,）
	 * @return
	 */
	String sendMsgToUser(String userId, String receiverUserId, String content, String msgType, String chatType);

	/**
	 * 保存系统通知
	 * 
	 * @param sender
	 *            :消息发送人
	 * @param title
	 *            :消息title
	 * @param type
	 *            ：消息类型
	 * @param content
	 *            ：消息内容
	 */
	void saveSystemNotification(String sender, String title, String type, String content, String isRead, String receiver);

	/**
	 * 分页查询系统通知
	 * 
	 * @param startTime
	 *            ：时间范围开始时间
	 * @param endTime
	 *            ：时间范围结束时间
	 * @param title
	 *            ：title查询信息
	 * @param page
	 *            ：页码
	 * @param userId 
	 * @return
	 */
	PageObject querySystemNotification(String isRead, Date startTime, Date endTime, String title, Integer page, String userId);

	/**
	 * 查询个人私聊消息
	 * @param privateMsgModel
	 * @return
	 */
	int queryPrivateUserMessage(PrivateMsgModel privateMsgModel);


	/**
	 * 获取被@人员
	 * @param groupId
	 * @param userId
	 * @return
	 */
	List<ATModel> getAtUser(String groupId, String userId);

	/**
	 * 插入被@人员
	 * @param atm
	 */
	void addAtUser(ATModel atm);
	
	/**
	 * 删除被@人员
	 * @param groupId
	 * @param userId
	 */
	void delAtUser(String groupId, String userId);

	/**
	 * 获取最近聊天历史消息
	 * @param rowStart
	 * @param chatUserId
	 * @param chatType
	 * @param page
	 * @return
	 */
	PageObject queryHisMsgs(String chatUserId, String chatType, int page, int rowStart);

	/**
	 * 获取最近聊天历史消息
	 * @param rowStart
	 * @param chatUserId
	 * @param chatType
	 * @param page
	 * @return
	 */
	List<?> loadMsg(String chatUserId, String chatType, int page, Integer rowStart);

	
}

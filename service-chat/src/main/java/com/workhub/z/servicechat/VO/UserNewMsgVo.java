package com.workhub.z.servicechat.VO;

import java.util.Date;

public class UserNewMsgVo {

    private String msgId;
    private String tableType;
    private String msgSener;
    private Date sendTime;
    private String msg;
    private String msgType;
    private String levels;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getMsgSener() {
        return msgSener;
    }

    public void setMsgSener(String msgSener) {
        this.msgSener = msgSener;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }
}

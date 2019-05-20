package com.workhub.z.servicechat.VO;

public class GroupFileVo {

    //文件id
    private String fileId;

    //文件名称
    private String fileName;

    //上传时间 yyyy-MM-dd hh:mm:ss
    private String time;

    //上传人
    private String reviser;

    //文件密级
    private String levels;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReviser() {
        return reviser;
    }

    public void setReviser(String reviser) {
        this.reviser = reviser;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }
}

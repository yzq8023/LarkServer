package com.github.hollykunge.security.common.vo;

import lombok.Data;

/**
 * @author zhhongyu
 * fastdfs上传文件后返回文件基本信息实体类
 */
@Data
public class FileInfoVO {
    /**
     * 附件id
     */
    private String fileId;
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 文件后缀名
     */
    private String fileExt;
    /**
     * 附件转码为可读取的路径
     */
    private String readPath;
}

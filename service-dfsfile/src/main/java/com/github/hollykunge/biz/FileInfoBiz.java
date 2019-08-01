package com.github.hollykunge.biz;

import com.ace.cache.annotation.Cache;
import com.alibaba.fastjson.JSON;
import com.github.hollykunge.comtants.FileComtants;
import com.github.hollykunge.entity.FileInfoEntity;
import com.github.hollykunge.entity.FileManageInf;
import com.github.hollykunge.mapper.FileInfoMapper;
import com.github.hollykunge.security.common.biz.BaseBiz;
import com.github.hollykunge.security.common.exception.BaseException;
import com.github.hollykunge.security.common.util.EntityUtils;
import com.github.hollykunge.security.common.vo.FileInfoVO;
import com.github.hollykunge.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件基础数据业务处理层
 *
 * @author zhhongyu
 * @since 2019-07-29
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileInfoBiz extends BaseBiz<FileInfoMapper, FileInfoEntity> {
    @Autowired
    private FastDFSClientWrapper dfsClient;


    @Override
    protected String getPageName() {
        return null;
    }

    /**
     * 上传非加密文件业务处理
     *
     * @param file
     * @return
     * @throws Exception
     */
    public FileInfoVO uploadFile(MultipartFile file) throws Exception {
        String md5Key = MD5Util.MD5(file.getBytes());
        String fileId = "";
        //先从缓存中获取文件
        fileId = ((FileInfoBiz) AopContext.currentProxy()).uploadFileCache(md5Key, null);
        FileInfoEntity fileInfoEntity = this.fileToEntity(file, "0");
        //如果缓存中没有该文件
        if (StringUtils.isEmpty(fileId)) {
            String path = dfsClient.uploadFile(file);
            fileInfoEntity.setPath(path);
            this.insertEntityAce(fileInfoEntity, md5Key);
        }
        //如果缓存中有该文件，则直接返回成功上传从而实现秒传效果
        fileInfoEntity.setId(fileId);
        FileInfoVO fileInfoVO = this.transferEntityToVo(fileInfoEntity);
        return fileInfoVO;
    }

    /**
     * 上传加密文件（采用base64加密方式）
     *
     * @param file          文件
     * @param sensitiveType 加密类型（1为base64加密，2为位移加密法）
     * @return
     * @throws Exception
     */
    public FileInfoVO uploadSensitiveFile(MultipartFile file, String sensitiveType) throws Exception {
        String md5Key = MD5Util.MD5(file.getBytes());
        String fileId = "";
        //先从缓存中获取文件
        fileId = ((FileInfoBiz) AopContext.currentProxy()).uploadFileCache(md5Key, null);
        FileInfoEntity fileInfoEntity = this.fileToEntity(file, sensitiveType);
        //如果缓存中没有该文件
        if (StringUtils.isEmpty(fileId)) {
            String path = "";
            //采用base64
            if (FileComtants.SENSITIVE_BASE64_TYPE.equals(sensitiveType)) {
                path = dfsClient.uploadbase64SensitiveFile(file);
            }
            //采用位移加密
            if (FileComtants.SENSITIVE_BYTEMOVE_TYPE.equals(sensitiveType)) {
                path = dfsClient.uploadByteMoveSensitiveFile(file);
            }
            fileInfoEntity.setPath(path);
            this.insertEntityAce(fileInfoEntity, md5Key);
            fileId = fileInfoEntity.getId();
        }
        //如果缓存中有该文件，则直接返回成功上传从而实现秒传效果
        fileInfoEntity.setId(fileId);
        FileInfoVO fileInfoVO = this.transferEntityToVo(fileInfoEntity);
        return fileInfoVO;
    }

    /**
     * 删除文件业务处理
     *
     * @param fileId 文件id
     */
    public void deleteFile(String fileId) {
        if (StringUtils.isEmpty(fileId)) {
            throw new BaseException("fileId is null...");
        }
        FileInfoEntity fileInfoEntity = new FileInfoEntity();
        fileInfoEntity.setId(fileId);
        fileInfoEntity = mapper.selectByPrimaryKey(fileInfoEntity);
        if (fileInfoEntity == null) {
            throw new BaseException("没有改文件...");
        }
        //保留文件历史，只是将数据库中文件信息设置为无效状态
        fileInfoEntity.setStatus(FileComtants.INVALID_FILE);
        mapper.updateByPrimaryKeySelective(fileInfoEntity);
    }

    /**
     * 文件下载业务处理
     *
     * @param fileId
     * @param sensitiveType
     * @return
     */
    public Map<String, Object> downLoadFile(String fileId, String sensitiveType) throws IOException {
        if (StringUtils.isEmpty(fileId)) {
            throw new BaseException("fileId is null ... ");
        }
        FileInfoEntity fileInfoEntity = new FileInfoEntity();
        fileInfoEntity.setId(fileId);
        fileInfoEntity = mapper.selectByPrimaryKey(fileInfoEntity);
        if (fileInfoEntity == null) {
            throw new BaseException("没有该文件...");
        }
        if (StringUtils.isEmpty(fileInfoEntity.getPath())) {
            throw new BaseException(fileInfoEntity.getId() + "数据中文件没有fastdfs路径");
        }
        //文件名称
        String fileName = fileInfoEntity.getFileName();
        //文件后缀
        String fileExt = fileInfoEntity.getFileExt();
        if (StringUtils.isEmpty(fileName)) {
            fileName = FileComtants.DOWNLOAD_FILE_NAME;
        }
        if (StringUtils.isEmpty(fileExt)) {
            fileExt = FileComtants.DOWNLOAD_FILE_EXT;
        }
        fileName = fileName + FileComtants.FILE_REGIX_VALUE + fileExt;
        byte[] fileIO = null;
        //非涉密文件下载
        if (FileComtants.NO_SENSITIVE_TYPE.equals(sensitiveType)) {
            fileIO = dfsClient.download(fileInfoEntity.getPath());
        }
        if (FileComtants.SENSITIVE_BASE64_TYPE.equals(sensitiveType)) {
            fileIO = dfsClient.downloadBase64SensitiveFile(fileInfoEntity.getPath());
        }
        if (FileComtants.SENSITIVE_BYTEMOVE_TYPE.equals(sensitiveType)) {
            fileIO = dfsClient.downloadByteMoveSensitiveFile(fileInfoEntity.getPath());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("fileName", fileName);
        result.put("fileByte", fileIO);
        return result;
    }

    /**
     * 获取图片，并响应给客户端
     *
     * @param fileId
     * @param response
     * @param sensitiveType
     * @throws IOException
     */
    public void getImg(String fileId, HttpServletResponse response, String sensitiveType) throws IOException {
        if (StringUtils.isEmpty(fileId)) {
            throw new BaseException("fileId is null ... ");
        }
        OutputStream outputStream = null;
        try {
            //读取路径下面的文件
            FileInfoEntity fileInfoEntity = new FileInfoEntity();
            fileInfoEntity.setId(fileId);
            fileInfoEntity = mapper.selectByPrimaryKey(fileInfoEntity);
            if (fileInfoEntity == null) {
                throw new BaseException("查询不到该文件 ... ");
            }
            if (StringUtils.isEmpty(fileInfoEntity.getPath())) {
                throw new BaseException("文件没有在fastdfs中 ... ");
            }
            byte[] data = null;
            if (FileComtants.NO_SENSITIVE_TYPE.equals(sensitiveType)) {
                data = dfsClient.download(fileInfoEntity.getPath());
            }
            if (FileComtants.SENSITIVE_BASE64_TYPE.equals(sensitiveType)) {
                data = dfsClient.downloadBase64SensitiveFile(fileInfoEntity.getPath());
            }
            if (FileComtants.SENSITIVE_BYTEMOVE_TYPE.equals(sensitiveType)) {
                data = dfsClient.downloadByteMoveSensitiveFile(fileInfoEntity.getPath());
            }
            //获取文件后缀名格式
            String ext = ((fileInfoEntity.getFileExt() == null) ? "" : fileInfoEntity.getFileExt());
            //判断图片格式,设置相应的输出文件格式
            if ("jpg".equals(ext) || "JPG".equals(ext)) {
                response.setContentType("image/jpeg");
            }
            if ("png".equals(ext) || "PNG".equals(ext)) {
                response.setContentType("image/png");
            }
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            log.error(CommonUtil.getExceptionMessage(e));
        } finally {
            //关流
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private String insertEntityAce(FileInfoEntity fileInfoEntity, String md5Key) throws Exception {
        //todo:欠一个redis和数据库事务一致性,这个方法可能不需要
        mapper.insertSelective(fileInfoEntity);
        //插入成功将唯一性的base64编码key缓存到缓存中
        String fileId = null;
        try {
            fileId = ((FileInfoBiz) AopContext.currentProxy()).uploadFileCache(md5Key, JSON.toJSONString(fileInfoEntity.getId()));
        } catch (Exception e) {
            log.error(CommonUtil.getExceptionMessage(e));
            dfsClient.deleteFile(fileInfoEntity.getPath());
            throw e;
        }
        return fileId;
    }

    /**
     * 将file文件转为数据库实体（path字段需要单独赋值）
     *
     * @param file
     * @return
     * @throws Exception
     */
    private FileInfoEntity fileToEntity(MultipartFile file, String sensitiveType) throws Exception {
        if (file == null) {
            throw new BaseException("上传文件不能为空...");
        }
        FileInfoEntity fileInfoEntity = new FileInfoEntity();
        String fileName = file.getOriginalFilename();
        String suffix = "";
        String fileExt = "";
        String regixValue = FileComtants.FILE_REGIX_VALUE;
        if (fileName.indexOf(regixValue) != -1) {
            suffix = fileName.substring(fileName.lastIndexOf(regixValue));
            fileName = fileName.substring(0, fileName.lastIndexOf(regixValue));
        }
        if (!"".equals(suffix) && !regixValue.equals(suffix)) {
            fileExt = suffix.substring(suffix.indexOf(regixValue) + 1);
        }
        fileInfoEntity.setFileExt(fileExt);
        fileInfoEntity.setFileName(fileName);
        String fileType = "";
        FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumByValue(fileExt);
        fileType = fileTypeEnum.getType();
        fileInfoEntity.setFileType(fileType.toLowerCase());
        fileInfoEntity.setFileSize(Double.valueOf(file.getSize()));
        fileInfoEntity.setFileEncrype(sensitiveType);
        //有效
        fileInfoEntity.setStatus(FileComtants.EFECTIVE_FILE);
        EntityUtils.setCreatAndUpdatInfo(fileInfoEntity);
        return fileInfoEntity;
    }

    private FileInfoVO transferEntityToVo(FileInfoEntity fileInfoEntity) {
        FileInfoVO fileInfoVO = new FileInfoVO();
        BeanUtils.copyProperties(fileInfoEntity, fileInfoVO);
        fileInfoVO.setFileId(fileInfoEntity.getId());
        return fileInfoVO;
    }

    @Cache(key = "files{1}", result = String.class)
    public String uploadFileCache(String base64FileName, String fileId) throws Exception {
        return fileId;
    }


    public static void main(String[] args) {
        System.out.println(JSON.toJSONString("654sdf"));
        String s = JSON.parseObject("\"654sdf\"", String.class);
        System.out.println(s);
    }
}

package com.github.hollykunge.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.util.FastDFSClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * file文件接口
 * @author zhhongyu
 * @since 2019-06-18
 */
@RestController
@RequestMapping("file")
public class FastDFSController {
    @Autowired
    private FastDFSClientWrapper dfsClient;

    /**
     * 上传接口
     * @param file file文件
     * @return 文件访问路径
     * @throws Exception
     */
    @PostMapping("/upload")
    @ResponseBody
    public ObjectRestResponse<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = dfsClient.uploadFile(file);
        return new ObjectRestResponse<>().data(imgUrl).rel(true);
    }

    /**
     * 删除文件接口
     * @param file 有效的路径样式为(group/path) 或者
     *       (http://ip/group/path),路径地址必须包含group
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Boolean> remove(@RequestParam String file) throws Exception {
        dfsClient.deleteFile(file);
        return new ObjectRestResponse<>().rel(true);
    }
}
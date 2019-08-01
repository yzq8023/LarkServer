package com.github.hollykunge.controller;

import com.github.hollykunge.biz.FileInfoBiz;
import com.github.hollykunge.entity.Chunk;
import com.github.hollykunge.entity.FileInfoEntity;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.service.ChunkService;
import com.github.hollykunge.util.FastDFSClientWrapper;
import com.github.hollykunge.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @description: 分块上传、断点续传
 * @author: dd
 * @since: 2019-08-01
 */
@RestController
@RequestMapping("chunk")
public class ChunkController {

    @Value("${prop.upload-folder}")
    private String uploadFolder;

    @Resource
    private ChunkService chunkService;

    @Resource
    private FileInfoBiz fileInfoBiz;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    @PostMapping("/chunk")
    public String uploadChunk(Chunk chunk) {
        MultipartFile file = chunk.getFile();

        try {
            byte[] bytes = file.getBytes();
            String path = dfsClient.uploadFile(file);
            //chunkService.saveChunk(chunk);

            return "完成";
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
    }

    @GetMapping("/chunk")
    public Object checkChunk(Chunk chunk, HttpServletResponse response) {
        if (chunkService.checkChunk(chunk.getIdentifier(), chunk.getChunkNumber())) {

        }

        return chunk;
    }

    @PostMapping("/mergeFile")
    public String mergeFile(FileInfoEntity fileInfo) {
        String filename = fileInfo.getFileName();
        String file = uploadFolder + "/" + fileInfo.getId() + "/" + filename;
        String folder = uploadFolder + "/" + fileInfo.getId();
        FileUtils.merge(file, folder, filename);
        fileInfo.setPath(file);
        fileInfoBiz.insert(fileInfo);

        return "完成";
    }
}

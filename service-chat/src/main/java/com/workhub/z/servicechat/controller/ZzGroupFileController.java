package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupInfo;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzGroupFile;
import com.workhub.z.servicechat.service.ZzGroupFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 群文件(ZzGroupFile)表控制层
 *
 * @author 忠
 * @since 2019-05-13 10:59:08
 */
@RestController
@RequestMapping("/zzGroupFile")
public class ZzGroupFileController {

    /**
     * 服务对象
     */
    @Resource
    private ZzGroupFileService zzGroupFileService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public ZzGroupFile selectOne(@RequestParam("id") String id) {
        return this.zzGroupFileService.queryById(id);
    }

    /**
     * 群文件查询
     * @param id
     * @return
     */
    @PostMapping("/groupfile")
    public TableResultResponse groupFileList(@RequestParam("id")String id,
                                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                                          @RequestParam(value = "size",defaultValue = "10")Integer size){
        PageInfo<GroupInfo> pageInfo = null;
        Long total = 0L;
        try {
            pageInfo = this.zzGroupFileService.groupFileList(id, page, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        T data, int pageSize, int pageNo, int totalPage, int totalCount
        return new TableResultResponse(pageInfo.getList(),pageInfo.getPageSize(),pageInfo.getPageNum(),pageInfo.getPages(),(int)pageInfo.getTotal());
    }


    /**
     * 文件删除(删记录)
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ObjectRestResponse delFileInfo(@RequestParam("id") String id){
        boolean flag = this.zzGroupFileService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }

    /**
     * 创建
     * @param zzGroupFile
     * @return
     */
    @PostMapping("/create")
    public ObjectRestResponse insert(@RequestParam("zzGroupFile")ZzGroupFile zzGroupFile){
        zzGroupFile.setFileId(RandomId.getUUID());
        zzGroupFile.setCreator("登陆人id");//TODO
        zzGroupFile.setCreateTime(new Date());
        Integer insert = this.zzGroupFileService.insert(zzGroupFile);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (insert == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    /**
     * 修改
     * @param zzGroupFile
     * @return
     */
    @PostMapping("/update")
    public ObjectRestResponse update(@RequestParam("zzGroupFile")ZzGroupFile zzGroupFile){
        zzGroupFile.setUpdator("登陆人id");//TODO
        zzGroupFile.setUpdateTime(new Date());
        Integer update = this.zzGroupFileService.update(zzGroupFile);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == 0){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }
}
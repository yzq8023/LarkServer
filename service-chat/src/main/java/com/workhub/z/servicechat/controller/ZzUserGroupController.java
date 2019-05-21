package com.workhub.z.servicechat.controller;

import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.msg.TableResultResponse;
import com.github.pagehelper.PageInfo;
import com.workhub.z.servicechat.VO.GroupListVo;
import com.workhub.z.servicechat.VO.GroupUserListVo;
import com.workhub.z.servicechat.config.RandomId;
import com.workhub.z.servicechat.entity.ZzAt;
import com.workhub.z.servicechat.entity.ZzUserGroup;
import com.workhub.z.servicechat.service.ZzUserGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户群组映射表(ZzUserGroup)表控制层
 *
 * @author 忠
 * @since 2019-05-10 14:22:54
 */
@RestController
@RequestMapping("/zzUserGroup")
public class ZzUserGroupController {
    /**
     * 服务对象
     */
    @Resource
    private ZzUserGroupService zzUserGroupService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public ZzUserGroup selectOne(String id) {
        return this.zzUserGroupService.queryById(id);
    }

    @PostMapping("/create")
    public ObjectRestResponse insert(ZzUserGroup zzUserGroup){
        zzUserGroup.setId(RandomId.getUUID());
        zzUserGroup.setCreatetime(new Date());
        Integer insert = this.zzUserGroupService.insert(zzUserGroup);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (insert == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @PostMapping("/update")
    public ObjectRestResponse update(ZzUserGroup zzUserGroup){
        Integer update = this.zzUserGroupService.update(zzUserGroup);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        if (update == null){
            objectRestResponse.data("失败");
            return objectRestResponse;
        }
        objectRestResponse.data("成功");
        return objectRestResponse;
    }

    @DeleteMapping("/delete")
    public ObjectRestResponse delete(@RequestParam("id")String id){
        boolean flag = this.zzUserGroupService.deleteById(id);
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        objectRestResponse.data(flag);
        return objectRestResponse;
    }

    @PostMapping("/querygroup")
    public TableResultResponse queryGroupList(@RequestParam("id")String id,
                                                  @RequestParam(value = "page",defaultValue = "1")Integer page,
                                                  @RequestParam(value = "size",defaultValue = "10")Integer size){
        PageInfo<GroupListVo> groupUserListVoPageInfo = null;
        try {
            groupUserListVoPageInfo = this.zzUserGroupService.groupUserList(id, page, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int pageSize, int pageNo, int totalPage, long totalCount, List<T> rows
        return new TableResultResponse(groupUserListVoPageInfo.getPageSize(),
                groupUserListVoPageInfo.getPageNum(),
                groupUserListVoPageInfo.getPages(),
                groupUserListVoPageInfo.getTotal(),
                groupUserListVoPageInfo.getList());
    }
}
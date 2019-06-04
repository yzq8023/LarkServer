package com.github.hollykunge.security.admin.rest;

import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.admin.biz.OrgBiz;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Org;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.OrgTree;
import com.github.hollykunge.security.admin.vo.RoleTree;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.util.TreeUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dk
 */

@Controller
@RequestMapping("org")
@Api("组织管理")
public class OrgController extends BaseController<OrgBiz, Org> {

    /**
     * 通过orgId获取所属用户
     *
     * @param id 组织id
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AdminUser>> getUsers(@RequestParam("orgId") String id) {
        return new ObjectRestResponse<List<AdminUser>>().rel(true).data(baseBiz.getOrgUsers(id));
    }

    /**
     * 通过orgId修改组织所属用户
     *
     * @param id    组织id
     * @param users 以逗号分隔的userId
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@RequestParam("orgId") String id,@RequestParam("users") String users) {
        baseBiz.modifyOrgUsers(id, users);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 根据父级id取下面的org
     * ps：如果parent为null时默认取root下的组织
     * @param parentTreeId 父级id
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<OrgTree>> tree(@RequestParam("parentTreeId") String parentTreeId) {
        if(StringUtils.isEmpty(parentTreeId)){
            parentTreeId = AdminCommonConstant.ROOT;
        }
        return new ListRestResponse("",0, getTree(baseBiz.selectListAll(), parentTreeId));
    }

    private List<OrgTree> getTree(List<Org> orgs, String parentTreeId) {
        List<OrgTree> trees = new ArrayList<OrgTree>();
        OrgTree node;
        for (Org org : orgs) {
            node = new OrgTree();
            String jsonNode = JSON.toJSONString(org);
            node = JSON.parseObject(jsonNode, OrgTree.class);
            node.setLabel(org.getOrgName());
            trees.add(node);
        }
        return TreeUtil.bulid(trees, parentTreeId);
    }
}
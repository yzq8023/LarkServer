package com.github.hollykunge.security.admin.rest;

import com.github.hollykunge.security.admin.biz.OrgBiz;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import com.github.hollykunge.security.admin.entity.Org;
import com.github.hollykunge.security.admin.vo.AdminUser;
import com.github.hollykunge.security.admin.vo.OrgTree;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.util.TreeUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AdminUser>> getUsers(@PathVariable String id) {
        return new ObjectRestResponse<List<AdminUser>>().rel(true).data(baseBiz.getOrgUsers(id));
    }

    /**
     * 通过orgId修改组织所属用户
     *
     * @param id    组织id
     * @param users 以逗号分隔的userId
     */
    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable String id, String users) {
        baseBiz.modifyOrgUsers(id, users);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 获取组织树
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public List<OrgTree> tree(String name) {
        Org org = new Org();
        org.setOrgName(name);
        return getTree(baseBiz.selectList(org), AdminCommonConstant.ROOT);
    }

    private List<OrgTree> getTree(List<Org> orgs, String root) {
        List<OrgTree> trees = new ArrayList<OrgTree>();
        OrgTree node;
        for (Org org : orgs) {
            node = new OrgTree();
            node.setLabel(org.getOrgName());
            BeanUtils.copyProperties(org, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees, root);
    }
}
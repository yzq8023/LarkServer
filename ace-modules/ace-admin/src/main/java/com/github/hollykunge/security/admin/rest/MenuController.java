package com.github.hollykunge.security.admin.rest;

import com.alibaba.fastjson.JSON;
import com.github.hollykunge.security.admin.biz.ElementBiz;
import com.github.hollykunge.security.admin.biz.MenuBiz;
import com.github.hollykunge.security.admin.biz.UserBiz;
import com.github.hollykunge.security.admin.entity.Menu;
import com.github.hollykunge.security.admin.vo.AdminElement;
import com.github.hollykunge.security.admin.vo.AuthorityMenuTree;
import com.github.hollykunge.security.admin.vo.MenuTree;
import com.github.hollykunge.security.admin.vo.OrgTree;
import com.github.hollykunge.security.common.msg.ListRestResponse;
import com.github.hollykunge.security.common.msg.ObjectRestResponse;
import com.github.hollykunge.security.common.rest.BaseController;
import com.github.hollykunge.security.common.util.TreeUtil;
import com.github.hollykunge.security.admin.constant.AdminCommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-12 8:49
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController<MenuBiz, Menu> {
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ElementBiz elementBiz;

    /**
     * 根据menuId获取element
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/element",method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<AdminElement>> listMenuElement(@RequestParam("menuId") String menuId){
        List<AdminElement> adminElements = elementBiz.listMenuElement(menuId);
        return new ListRestResponse<>("",adminElements.size(),adminElements);
    }

    /**
     * 给menu添加element或修改element
     * @param elementList 要绑定的element
     * @return
     */
    @RequestMapping(value = "/element",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyMenuElement(@RequestParam("menuId") String menuId, @RequestBody List<AdminElement> elementList){
        elementBiz.modifyMenuElement(menuId,elementList);
        return new ObjectRestResponse().rel(true);
    }

    /**
     * 通过父级menuid获取下面的子菜单
     * @param parentTreeId 父级treeId
     * @return 父级下的子菜单列表
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public ListRestResponse<List<MenuTree>> getTree(@RequestParam("parentTreeId") String parentTreeId) {
        if(StringUtils.isEmpty(parentTreeId)){
            parentTreeId = AdminCommonConstant.ROOT;
        }
        List<MenuTree> menuTree = getMenuTree(baseBiz.selectListAll(), parentTreeId);
        return new ListRestResponse<>("",menuTree.size(),menuTree);
    }

    private List<MenuTree> getMenuTree(List<Menu> menus,String root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (Menu menu : menus) {
            node = new MenuTree();
            node.setLabel(menu.getTitle());
            String jsonNode = JSON.toJSONString(menu);
            node = JSON.parseObject(jsonNode, MenuTree.class);
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root) ;
    }
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Menu> list(String title) {
//        Example example = new Example(Menu.class);
//        if (StringUtils.isNotBlank(title)) {
//            example.createCriteria().andLike("title", "%" + title + "%");
//        }
//        return baseBiz.selectByExample(example);

//    }

//    @RequestMapping(value = "/system", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Menu> getSystem() {
//        Menu menu = new Menu();
//        menu.setParentId(AdminCommonConstant.ROOT);
//        return baseBiz.selectList(menu);
//    }
//
//    @RequestMapping(value = "/menuTree", method = RequestMethod.GET)
//    @ResponseBody
//    public List<MenuTree> listMenu(String parentId) {
//        try {
//            if (parentId == null) {
//                parentId = this.getSystem().get(0).getId();
//            }
//        } catch (Exception e) {
//            return new ArrayList<MenuTree>();
//        }
//        List<MenuTree> trees = new ArrayList<MenuTree>();
//        MenuTree node = null;
//        Example example = new Example(Menu.class);
//        Menu parent = baseBiz.selectById(parentId);
//        example.createCriteria().andLike("path", parent.getPath() + "%").andNotEqualTo("id",parent.getId());
//        return getMenuTree(baseBiz.selectByExample(example), parent.getId());
//    }
//
//    @RequestMapping(value = "/authorityTree", method = RequestMethod.GET)
//    @ResponseBody
//    public List<AuthorityMenuTree> listAuthorityMenu() {
//        List<AuthorityMenuTree> trees = new ArrayList<AuthorityMenuTree>();
//        AuthorityMenuTree node = null;
//        for (Menu menu : baseBiz.selectListAll()) {
//            node = new AuthorityMenuTree();
//            node.setText(menu.getTitle());
//            BeanUtils.copyProperties(menu, node);
//            trees.add(node);
//        }
//        return TreeUtil.bulid(trees, AdminCommonConstant.ROOT);
//    }
//
//    @RequestMapping(value = "/user/authorityTree", method = RequestMethod.GET)
//    @ResponseBody
//    public List<MenuTree> listUserAuthorityMenu(String parentId){
//        String userId = userBiz.getUserByUserId(getCurrentUserName()).getId();
//        try {
//            if (parentId == null) {
//                parentId = this.getSystem().get(0).getId();
//            }
//        } catch (Exception e) {
//            return new ArrayList<MenuTree>();
//        }
//        return getMenuTree(baseBiz.getUserAuthorityMenuByUserId(userId),parentId);
//    }
//
//    @RequestMapping(value = "/user/system", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Menu> listUserAuthoritySystem() {
//        String userId = userBiz.getUserByUserId(getCurrentUserName()).getId();
//        return baseBiz.getUserAuthoritySystemByUserId(userId);
//    }

}

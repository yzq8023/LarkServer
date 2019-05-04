package com.github.hollykunge.security.api.vo.authority;

/**
 * 菜单内部功能控制实体
 *
 * @author: holly
 * @since: 2019/4/30
 */
public class ActionEntitySet {

    //功能add、query、get、update、delete
    private String action;
    //描述：新增、查询、详情、修改、删除
    private String describe;
    //默认选中
    private Boolean defaultCheck;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getDefaultCheck() {
        return defaultCheck;
    }

    public void setDefaultCheck(Boolean defaultCheck) {
        this.defaultCheck = defaultCheck;
    }
}

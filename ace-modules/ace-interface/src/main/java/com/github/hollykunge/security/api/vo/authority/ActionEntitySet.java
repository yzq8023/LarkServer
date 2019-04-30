package com.github.hollykunge.security.api.vo.authority;

/**
 * 菜单内部功能控制
 *
 * @author: holly
 * @since: 2019/4/30
 */
public class ActionEntitySet {

    private String action;
    private String describe;
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

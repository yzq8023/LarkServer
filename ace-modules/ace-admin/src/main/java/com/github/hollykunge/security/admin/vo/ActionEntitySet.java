package com.github.hollykunge.security.admin.vo;

import lombok.Data;

/**
 * 菜单内部功能控制实体
 *
 * @author: holly
 * @since: 2019/5/9
 */
@Data
public class ActionEntitySet {

    /**
     * action->method
     * 功能add、query、get、update、delete
     */
    private String method;
    /**
     * describe->description
     * 描述：新增、查询、详情、修改、删除
     */
    private String description;
    /**
     * 默认选中
     */
    private Boolean defaultCheck;
}

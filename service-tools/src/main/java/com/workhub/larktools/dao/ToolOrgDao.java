package com.workhub.larktools.dao;

import com.workhub.larktools.entity.ToolTree;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface ToolOrgDao extends Mapper<ToolTree> {
    /**
    * @MethodName: add
     * @Description: 添加树组织结构
     * @Param: [param]组织名称 ：name ，父节点pid如果为空认为是根节点；userId登录用户；order 排序
     * @Return: int
     * @Author: zhuqz
     * @Date: 2019/8/13
    **/
    int add(@Param("params") Map param);
    /**
    * @MethodName: addTreeOrg
     * @Description: 添加组织和工具树的映射关系
     * @Param: [param] userId、orgCode、treeId树的根节点id、id主键
     * @Return: int
     * @Author: admin
     * @Date: 2019/8/13
    **/
    int addTreeOrg(@Param("params") Map param);
}

package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.Role;
import com.github.hollykunge.security.admin.vo.ActionEntitySet;
import com.github.hollykunge.security.admin.vo.FrontPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>角色管理mapper接口</p>
 * @author zhhongyu
 */
public interface RoleMapper extends Mapper<Role> {

}
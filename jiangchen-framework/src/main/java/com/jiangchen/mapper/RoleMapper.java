package com.jiangchen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangchen.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-11 16:56:52
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}


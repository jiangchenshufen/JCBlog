package com.jiangchen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangchen.domain.entity.RoleMenu;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-05 14:53:02
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Long> selectByRoleId(Long roleId);
}


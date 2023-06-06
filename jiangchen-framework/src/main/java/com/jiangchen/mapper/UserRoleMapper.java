package com.jiangchen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangchen.domain.entity.UserRole;

import java.util.ArrayList;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-06 10:19:50
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据userId删除所有
     * @param userId
     * @return
     */
    void deleteAllById(Long userId);

    int selectAllById(Long userId);
}


package com.jiangchen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangchen.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-14 15:39:14
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id查询关联的角色ids
     * @param userId
     * @return
     */
    List<Long> selectRoleIdsById(Long userId);
}

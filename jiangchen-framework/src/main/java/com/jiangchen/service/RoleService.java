package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RoleAddDeo;
import com.jiangchen.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-05-11 16:56:52
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    /**
     * 角色列表分页查询的功能
     * 角色名称进行模糊查询
     * 状态进行查询
     * @param pageNum
     * @param pageSize
     * @param roleName
     * @param status
     * @return
     */
    ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status);

    /**
     * 新增角色
     * @param roleAddDeo
     * @return
     */
    ResponseResult addRole(RoleAddDeo roleAddDeo);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    ResponseResult selectRoleById(Long id);
}


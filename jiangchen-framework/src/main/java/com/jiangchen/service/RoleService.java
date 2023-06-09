package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RoleAddDeo;
import com.jiangchen.domain.dto.RoleChangeStatusDto;
import com.jiangchen.domain.entity.Role;
import com.jiangchen.domain.vo.SaveRoleVo;

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

    /**
     * 更新角色信息
     * @param saveRoleVo
     * @return
     */
    ResponseResult saveRole(SaveRoleVo saveRoleVo);

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    ResponseResult delRoleById(Long id);

    /**
     * 根据roleId修改状态
     * @param roleChangeStatusDto
     * @return
     */
    ResponseResult changeStatus(RoleChangeStatusDto roleChangeStatusDto);

    /**
     * 查询的是所有状态正常的角色
     * @return
     */
    ResponseResult statusNormalRoleList();

}


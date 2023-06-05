package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RoleAddDeo;
import com.jiangchen.domain.dto.RoleChangeStatusDto;
import com.jiangchen.domain.vo.SaveRoleVo;
import com.jiangchen.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status){
       return roleService.roleList(pageNum, pageSize, roleName, status);
    }

    @ApiOperation("新增角色")
    @PostMapping()
    public ResponseResult addRole(@RequestBody RoleAddDeo roleAddDeo){
        return roleService.addRole(roleAddDeo);
    }

    @ApiOperation("根据id查询角色")
    @GetMapping("{id}")
    public ResponseResult selectRoleById(@PathVariable("id") Long id){
        return roleService.selectRoleById(id);
    }

    @ApiOperation("更新角色")
    @PutMapping
    public ResponseResult saveRole(@RequestBody SaveRoleVo saveRoleVo){
        return roleService.saveRole(saveRoleVo);
    }

    @ApiOperation("根据id删除角色")
    @DeleteMapping("{id}")
    public ResponseResult delRoleById(@PathVariable("id") Long id){
        return roleService.delRoleById(id);
    }

    @ApiOperation("根据id修改状态")
    @PutMapping("/changeStatus")
    public ResponseResult updateRole(@RequestBody RoleChangeStatusDto roleChangeStatusDto){
        return roleService.changeStatus(roleChangeStatusDto);
    }
}

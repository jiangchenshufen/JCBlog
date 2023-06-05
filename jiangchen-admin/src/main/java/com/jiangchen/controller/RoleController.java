package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RoleAddDeo;
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

}

package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RegisterUserDto;
import com.jiangchen.domain.dto.UpdateUserDto;
import com.jiangchen.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("分页用户列表模糊搜素")
    @GetMapping("/list")
    public ResponseResult userList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status){
        return userService.userlist(pageNum, pageSize, userName, phonenumber, status);
    }

    @ApiOperation("注册用户")
    @PostMapping()
    public ResponseResult registerUser(@RequestBody RegisterUserDto registerUserDto){
        return userService.registerUser(registerUserDto);
    }

    @ApiOperation("根据id删除用户")
    @DeleteMapping("{id}")
    public ResponseResult delUserById(@PathVariable("id") String id){
        return userService.delUserById(id);
    }

    @ApiOperation("根据id查询用户信息")
    @GetMapping("{id}")
    public ResponseResult selectUserById(@PathVariable("id") Long id){
        return userService.selectUserById(id);
    }

    @ApiOperation("更新用户信息")
    @PutMapping()
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }

}

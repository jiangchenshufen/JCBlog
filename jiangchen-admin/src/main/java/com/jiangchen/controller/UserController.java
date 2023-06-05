package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RegisterUserDto;
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

}

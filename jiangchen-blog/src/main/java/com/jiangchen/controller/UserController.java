package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateUserInfoDto;
import com.jiangchen.domain.dto.UserRegisterDto;
import com.jiangchen.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 显示用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserRegisterDto userRegisterDto){
        return userService.register(userRegisterDto);
    }

    /**
     * 修改用户信息
     * @param updateUserInfoDto
     * @return
     */
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody UpdateUserInfoDto updateUserInfoDto ){
        return userService.updateUserInfo(updateUserInfoDto);
    }
}

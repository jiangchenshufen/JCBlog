package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateUserInfoDto;
import com.jiangchen.domain.dto.UserRegisterDto;
import com.jiangchen.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-15 16:11:10
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();


    /**
     * 用户注册
     * @param userRegisterDto
     * @return
     */
    ResponseResult register(UserRegisterDto userRegisterDto);

    /**
     * 修改用户信息
     * @param updateUserInfoDto
     * @return
     */
    ResponseResult updateUserInfo(UpdateUserInfoDto updateUserInfoDto);
}

package com.jiangchen.service;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    /**
     * 退出登录
     * @return
     */
    ResponseResult logout();
}

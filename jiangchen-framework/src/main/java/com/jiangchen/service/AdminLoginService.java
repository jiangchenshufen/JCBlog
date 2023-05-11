package com.jiangchen.service;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);
}

package com.jiangchen.service;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);
}

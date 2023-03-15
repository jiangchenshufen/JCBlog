package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.domain.entity.User;
import com.jiangchen.mapper.UserMapper;
import com.jiangchen.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-15 16:11:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

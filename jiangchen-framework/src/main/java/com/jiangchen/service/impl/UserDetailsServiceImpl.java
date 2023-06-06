package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.entity.LoginUser;
import com.jiangchen.domain.entity.User;
import com.jiangchen.mapper.MenuMapper;
import com.jiangchen.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据username查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户不存在");
        }
        if (user.getType().equals(SystemConstants.ADMIN)){
            List<String> menus = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,menus);
        }
        return new LoginUser(user,null);
    }
}

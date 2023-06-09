package com.jiangchen.service.impl;

import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.LoginUser;
import com.jiangchen.domain.entity.User;
import com.jiangchen.domain.vo.BlogUserLoginVo;
import com.jiangchen.domain.vo.UserInfoVo;
import com.jiangchen.service.AdminLoginService;
import com.jiangchen.service.BlogLoginService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.JwtUtil;
import com.jiangchen.utils.RedisCache;
import com.jiangchen.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (ObjectUtils.isEmpty(authenticate)){
            throw new RuntimeException("用户名或密码错误！");
        }
        //获取userId
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //生成token
        String token = JwtUtil.createJWT(userId);
        //存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_ADMIN_LOGIN_PREFIX +userId,loginUser);
        //把token封装 返回
        HashMap<String, String> map = new HashMap<>();
        map.put(SystemConstants.LOGIN_AUTHENTICATION_TOKEN,token);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject(SystemConstants.REDIS_ADMIN_LOGIN_PREFIX + userId);
        return ResponseResult.okResult();
    }

}

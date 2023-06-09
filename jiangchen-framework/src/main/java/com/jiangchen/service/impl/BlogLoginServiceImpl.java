package com.jiangchen.service.impl;

import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.LoginUser;
import com.jiangchen.domain.entity.User;
import com.jiangchen.domain.vo.BlogUserLoginVo;
import com.jiangchen.domain.vo.UserInfoVo;
import com.jiangchen.service.BlogLoginService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.JwtUtil;
import com.jiangchen.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject(SystemConstants.REDIS_LOGIN_PREFIX +userId,loginUser);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.okResult(new BlogUserLoginVo(token, userInfoVo));
    }

    @Override
    public ResponseResult loginOut() {
        //获取token解析得到userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //根据userId删除对应redis
        redisCache.deleteObject(SystemConstants.REDIS_LOGIN_PREFIX+userId);
        return ResponseResult.okResult();
    }
}

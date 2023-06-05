package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateUserInfoDto;
import com.jiangchen.domain.dto.UserRegisterDto;
import com.jiangchen.domain.entity.User;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.domain.vo.UserInfoVo;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.exception.SystemException;
import com.jiangchen.mapper.UserMapper;
import com.jiangchen.service.UserService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-15 16:11:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult register(UserRegisterDto userRegisterDto) {
        //对数据进行非空判断
        registrationInfoEmpty(userRegisterDto);
        //判断是否存在
        infoWhetherExists(userRegisterDto);
        String encodePassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userRegisterDto.setPassword(encodePassword);
        User user = BeanCopyUtils.copyBean(userRegisterDto, User.class);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateUserInfo(UpdateUserInfoDto updateUserInfoDto) {
        if (ObjectUtils.isEmpty(updateUserInfoDto.getAvatar())) {
            throw new SystemException(AppHttpCodeEnum.AVATAR_NOT_NULL);
        }
        if (ObjectUtils.isEmpty(updateUserInfoDto.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        User user = BeanCopyUtils.copyBean(updateUserInfoDto, User.class);
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userlist(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(userName),User::getUserName,userName);
        wrapper.eq(!ObjectUtils.isEmpty(phonenumber),User::getPhonenumber,phonenumber);
        wrapper.eq(!ObjectUtils.isEmpty(status),User::getStatus,status);
        Page<User> page = new Page<>();
        page.setCurrent(pageNum).setSize(pageSize);
        page(page,wrapper);
        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    private void infoWhetherExists(UserRegisterDto userRegisterDto){
        if (userNameExist(userRegisterDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(userRegisterDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(userRegisterDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
    }

    private boolean emailExist(String email){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail,email);
        return count(wrapper) > 0;
    }

    private boolean nickNameExist(String nickname){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName,nickname);
        return count(wrapper) > 0;
    }

    private boolean userNameExist(String userName){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,userName);
        return count(wrapper) > 0;
    }

    private void registrationInfoEmpty(UserRegisterDto userRegisterDto){
        //对数据进行非空判断
        if (!StringUtils.hasText(userRegisterDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
    }
}

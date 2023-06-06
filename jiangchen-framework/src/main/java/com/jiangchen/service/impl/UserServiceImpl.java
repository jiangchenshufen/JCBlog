package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RegisterUserDto;
import com.jiangchen.domain.dto.UpdateUserDto;
import com.jiangchen.domain.dto.UpdateUserInfoDto;
import com.jiangchen.domain.dto.UserRegisterDto;
import com.jiangchen.domain.entity.Role;
import com.jiangchen.domain.entity.User;
import com.jiangchen.domain.entity.UserRole;
import com.jiangchen.domain.vo.*;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.exception.SystemException;
import com.jiangchen.mapper.UserMapper;
import com.jiangchen.mapper.UserRoleMapper;
import com.jiangchen.service.RoleService;
import com.jiangchen.service.UserRoleService;
import com.jiangchen.service.UserService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private UserRoleMapper userRoleMapper;

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

    @Override
    public ResponseResult registerUser(RegisterUserDto registerUserDto) {
        userRegisterAssertion(registerUserDto);
        User user = BeanCopyUtils.copyBean(registerUserDto, User.class);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        if (save(user)){
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult delUserById(String id) {
        String [] ids = id.split(",");
        if (getBaseMapper().deleteBatchIds(Arrays.asList(ids)) > ids.length-1){
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult selectUserById(Long id) {
        UserMapper mapper = getBaseMapper();
        //获取roleIds
        List<Long> roleIds = mapper.selectRoleIdsById(id);
        List<Role> roleList = roleService.list();
        List<roleAdminVo> roleAdminVos = BeanCopyUtils.copyBeanList(roleList, roleAdminVo.class);
        //获取user信息
        UserAdminVo userAdminVo = BeanCopyUtils.copyBean(mapper.selectById(id), UserAdminVo.class);
        return ResponseResult.okResult(new UserRoleVo(roleIds,roleAdminVos,userAdminVo));
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        //去除roleIds的空数据
        List<Long> roleIds = updateUserDto.getRoleIds().stream().filter(roleId -> com.baomidou.mybatisplus.core.toolkit.ObjectUtils.isNotNull(roleId)).collect(Collectors.toList());
        //保存用户信息
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        if (!updateById(user)){
            throw new RuntimeException("用户信息保存失败");
        }
        //保存用户关联的角色
        Long userId = updateUserDto.getId();
        if (userRoleMapper.selectAllById(userId) > 0) {
            userRoleMapper.deleteAllById(userId);
        }
        ArrayList<UserRole> userRoleList = new ArrayList<>();
//        for (Long roleId : roleIds) {
//            userRoleList.add(new UserRole(user.getId(),roleId));
//        }
        roleIds.stream().forEach(roleId -> userRoleList.add(new UserRole(user.getId(),roleId)));
        if (!userRoleService.saveBatch(userRoleList)){
            throw new RuntimeException("用户角色信息保存失败");
        }
        return ResponseResult.okResult();
    }

    /**
     * 断言
     * @param registerUserDto
     */
    private void userRegisterAssertion(RegisterUserDto registerUserDto) {
        UserRegisterDto userRegisterDto = BeanCopyUtils.copyBean(registerUserDto, UserRegisterDto.class);
        if (!StringUtils.hasText(userRegisterDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegisterDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        infoWhetherExists(userRegisterDto);
        if (phonenumberExist(registerUserDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
    }

    private boolean phonenumberExist(String phonenumber) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhonenumber,phonenumber);
        return count(wrapper) > 0;
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

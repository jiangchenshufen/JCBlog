package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.LoginUser;
import com.jiangchen.domain.entity.User;
import com.jiangchen.domain.vo.AdminUserInfoVo;
import com.jiangchen.domain.vo.RoutersVo;
import com.jiangchen.domain.vo.UserInfoVo;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.exception.SystemException;
import com.jiangchen.service.AdminLoginService;
import com.jiangchen.service.BlogLoginService;
import com.jiangchen.service.MenuService;
import com.jiangchen.service.RoleService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "博客登录",description = "博客登录相关接口")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeys = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装数据
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeys,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结构是tree的形式
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menuService.selectRouterMenuTreeByUserId(userId)));
    }
}

package com.jiangchen.controller;

import com.jiangchen.annotation.SystemLog;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation("菜单列表")
    @GetMapping("/list")
    @SystemLog(businessName = "显示菜单列表")
    public ResponseResult menuList(String menuName, String status) {
        return menuService.menuList(menuName, status);
    }

}

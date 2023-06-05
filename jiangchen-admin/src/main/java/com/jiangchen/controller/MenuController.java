package com.jiangchen.controller;

import com.jiangchen.annotation.SystemLog;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.MenuDto;
import com.jiangchen.domain.entity.Menu;
import com.jiangchen.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("新增菜单")
    @PostMapping()
    public ResponseResult addMenu(@RequestBody MenuDto menuDto){
        return menuService.addMenu(menuDto);
    }

    @ApiOperation("根据id查询菜单")
    @GetMapping("{id}")
    public ResponseResult selectMenuById(@PathVariable("id") Long id){
        return menuService.selectMenuById(id);
    }

    @ApiOperation("保存菜单")
    @PutMapping()
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);//TODO 前端调用错误
    }

    @ApiOperation("根据id查询菜单")
    @DeleteMapping("{id}")
    public ResponseResult delMenuById(@PathVariable("id") Long id){
        return menuService.delMenuById(id);
    }

    @ApiOperation("获取菜单树")
    @GetMapping("/treeselect")
    public ResponseResult selectMenuTree(){
        return menuService.selectMenuTree();
    }


    @ApiOperation("根据id查询菜单树")
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuSelectTreeById(@PathVariable("id") Long id){
        return menuService.roleMenuSelectTreeById(id);
    }

}

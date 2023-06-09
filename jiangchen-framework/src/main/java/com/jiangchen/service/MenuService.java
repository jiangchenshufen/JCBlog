package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.MenuDto;
import com.jiangchen.domain.entity.Menu;
import com.jiangchen.domain.vo.MenuVo;
import com.jiangchen.domain.vo.RoutersVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-05-11 16:48:36
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long id);

    /**
     * 菜单列表接口
     * @param menuName
     * @param status
     * @return
     */
    ResponseResult menuList(String menuName, String status);

    /**
     * 新增菜单
     * @param menuDto
     * @return
     */
    ResponseResult addMenu(MenuDto menuDto);

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    ResponseResult selectMenuById(Long id);

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    ResponseResult delMenuById(Long id);

    /**
     * 获取权限对象树
     * @return
     */
    ResponseResult selectMenuTree();

    /**
     * 根据id查询菜单树
     * @param id
     * @return
     */
    ResponseResult roleMenuSelectTreeById(Long id);
}


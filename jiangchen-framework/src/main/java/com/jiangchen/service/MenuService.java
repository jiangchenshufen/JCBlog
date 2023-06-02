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
}


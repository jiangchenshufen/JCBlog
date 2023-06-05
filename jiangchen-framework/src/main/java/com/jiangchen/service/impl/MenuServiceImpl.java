package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.MenuDto;
import com.jiangchen.domain.entity.Menu;
import com.jiangchen.domain.vo.MenuKeysVo;
import com.jiangchen.domain.vo.MenuTreeVo;
import com.jiangchen.domain.vo.MenuVo;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.mapper.MenuMapper;
import com.jiangchen.mapper.RoleMenuMapper;
import com.jiangchen.service.MenuService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-05-11 16:48:37
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员直接返回所=所有权限
        List<String> adminPerms = isAdmin(id);
        if (Objects.nonNull(adminPerms)) {
            return adminPerms;
        }
        //否则返回所具有的所有权限
        return menuMapper.selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long id) {
        List<MenuVo> menuVos = null;
        if (SecurityUtils.isAdmin()) {
            menuVos = menuMapper.selectAllRouterMenu();
        } else {
            menuVos = menuMapper.selectRouterMenuTreeByUserId(id);
        }
        //构建树
        List<MenuVo> menuTree = builderMenuTree(menuVos, 0L);
        return menuTree;
    }

    @Override
    public ResponseResult menuList(String menuName, String status) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotNull(menuName), Menu::getMenuName, menuName);
        wrapper.eq(ObjectUtils.isNotNull(status), Menu::getStatus, status);
        //升序排序
        wrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menuList = list(wrapper);
        if (ObjectUtils.isNotNull(menuList)) {
            return ResponseResult.okResult(menuList);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult addMenu(MenuDto menuDto) {
        return save(BeanCopyUtils.copyBean(menuDto, Menu.class)) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SAVE_FALSE);
    }

    @Override
    public ResponseResult selectMenuById(Long id) {
        Menu menu = menuMapper.selectById(id);
        MenuDto menuVo = BeanCopyUtils.copyBean(menu, MenuDto.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (!menu.getParentId().equals(menu.getId())) {
            updateById(menu);
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"不能将目标菜单放入目标菜单下");
        }
    }

    @Override
    public ResponseResult delMenuById(Long id) {
        if (getBaseMapper().deleteById(id) > 0){
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult selectMenuTree() {
        return ResponseResult.okResult(createTree());

    }

    @Override
    public ResponseResult roleMenuSelectTreeById(Long id) {
        List<Long> menuKeys = roleMenuMapper.selectByRoleId(id);
        return ResponseResult.okResult(new MenuKeysVo(createTree(),menuKeys));
    }

    /**
     * 创建菜单树
     * @return
     */
    public List<MenuTreeVo> createTree(){
        List<Menu> menus = getBaseMapper().selectAllMenu();
        ArrayList<MenuTreeVo> menuTreeVos = new ArrayList<>();
        for (Menu menu : menus) {
            menuTreeVos.add(new MenuTreeVo(menu.getId(),menu.getParentId(),menu.getMenuName(),null));
        }
        return builderMenuTrees(menuTreeVos, 0L);
    }

    private List<MenuTreeVo> builderMenuTrees(List<MenuTreeVo> menuTreeVos, Long parentId) {
        return menuTreeVos.stream()
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(parentId))
                .map(menuTreeVo -> menuTreeVo.setChildren(getTreeChildren(menuTreeVo, menuTreeVos)))
                .collect(Collectors.toList());
    }


    private List<MenuTreeVo> getTreeChildren(MenuTreeVo menuTreeVo, List<MenuTreeVo> menuTreeVos) {
        return menuTreeVos.stream()
                .filter(menuTre -> menuTre.getParentId().equals(menuTreeVo.getId()))
                .map(menuTree -> menuTree.setChildren(getTreeChildren(menuTree,menuTreeVos)))
                .collect(Collectors.toList());
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos, Long parentId) {
        List<MenuVo> menuTree = menuVos.stream().filter(menuVo -> menuVo.getParentId().equals(parentId))
                .map(menuVo -> menuVo.setChildren(getChildren(menuVo, menuVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入list的子菜单
     *
     * @param menuVo
     * @param menuVos
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menuVo, List<MenuVo> menuVos) {
        return menuVos.stream()
                .filter(menu -> menu.getParentId().equals(menuVo.getId()))
                .map(menu -> menu.setChildren(getChildren(menu, menuVos)))
                .collect(Collectors.toList());
    }

    public List<String> isAdmin(Long id) {
        if (id.equals(1L)) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menuList = list(wrapper);
            List<String> collect = menuList.stream().map(Menu::getPerms).collect(Collectors.toList());
            return collect;
        }
        return null;
    }
}


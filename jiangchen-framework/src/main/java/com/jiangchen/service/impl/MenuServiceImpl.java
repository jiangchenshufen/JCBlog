package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.entity.Menu;
import com.jiangchen.domain.vo.MenuVo;
import com.jiangchen.mapper.MenuMapper;
import com.jiangchen.service.MenuService;
import com.jiangchen.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员直接返回所=所有权限
        List<String> adminPerms = isAdmin(id);
        if (Objects.nonNull(adminPerms)){
            return adminPerms;
        }
        //否则返回所具有的所有权限
        return menuMapper.selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long id) {
        List<MenuVo> menuVos = null;
        if (SecurityUtils.isAdmin()){
            menuVos = menuMapper.selectAllRouterMenu();
        }else{
            menuVos = menuMapper.selectRouterMenuTreeByUserId(id);
        }
        //构建树
        List<MenuVo> menuTree =  builderMenuTree(menuVos,0L);
        return menuTree;
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos,Long parentId) {
        List<MenuVo> menuTree = menuVos.stream().filter(menuVo -> menuVo.getParentId().equals(parentId))
                .map(menuVo -> menuVo.setChildren(getChildren(menuVo, menuVos)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入list的子菜单
     * @param menuVo
     * @param menuVos
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menuVo, List<MenuVo> menuVos) {
        return menuVos.stream()
                .filter(menu -> menu.getParentId().equals(menuVo.getId()))
                .map(menu -> menu.setChildren(getChildren(menu,menuVos)))
                .collect(Collectors.toList());
    }

    public List<String> isAdmin(Long id){
        if (id.equals(1L)){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menuList = list(wrapper);
            List<String> collect = menuList.stream().map(Menu::getPerms).collect(Collectors.toList());
            return collect;
        }
        return null;
    }
}


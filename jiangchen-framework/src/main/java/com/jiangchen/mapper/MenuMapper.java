package com.jiangchen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiangchen.domain.entity.Menu;
import com.jiangchen.domain.vo.MenuTreeVo;
import com.jiangchen.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-11 16:48:36
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<MenuVo> selectAllRouterMenu();

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectAllMenu();

    List<Menu> roleMenuSelectTreeById(Long roleId);
}


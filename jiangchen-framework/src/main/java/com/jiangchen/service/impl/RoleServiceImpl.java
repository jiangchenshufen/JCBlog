package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.RoleAddDeo;
import com.jiangchen.domain.entity.Role;
import com.jiangchen.domain.entity.RoleMenu;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.domain.vo.RoleVo;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.mapper.RoleMapper;
import com.jiangchen.service.RoleMenuService;
import com.jiangchen.service.RoleService;
import com.jiangchen.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-05-11 16:56:52
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        List<String> roleKey = isAdmin(id);
        if (!ObjectUtils.isEmpty(roleKey)){
            return roleKey;
        }
        //否则开始查询该用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(roleName),Role::getRoleName,roleName);
        wrapper.eq(!ObjectUtils.isEmpty(status),Role::getStatus,status);
        wrapper.orderByAsc(Role::getRoleSort);
        Page<Role> rolePage = new Page<>();
        rolePage.setCurrent(pageNum).setSize(pageSize);
        Page<Role> page = page(rolePage, wrapper);
        PageVo pageVo = new PageVo(BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addRole(RoleAddDeo roleAddDeo) {
        Role role = BeanCopyUtils.copyBean(roleAddDeo, Role.class);
        if (!save(role)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        ArrayList<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : roleAddDeo.getMenuIds()) {
            roleMenus.add(new RoleMenu(role.getId(),menuId));
        }
        if (!roleMenuService.saveBatch(roleMenus)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }

    private List<String> isAdmin(Long id) {
        if (id.equals(1L)){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(SystemConstants.ROLE_KEY);
            return arrayList;
        }
        return null;
    }
}


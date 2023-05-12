package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.entity.Role;
import com.jiangchen.mapper.RoleMapper;
import com.jiangchen.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        List<String> roleKey = isAdmin(id);
        if (!ObjectUtils.isEmpty(roleKey)){
            return roleKey;
        }
        //否则开始查询该用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
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


package com.jiangchen.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleVo {

    private List<Long> roleIds;

    private List<roleAdminVo> roles;

    private UserAdminVo user;

}

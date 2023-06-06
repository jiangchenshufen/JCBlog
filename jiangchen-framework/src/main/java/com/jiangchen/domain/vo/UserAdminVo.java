package com.jiangchen.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminVo {

    private Long id;

    private String userName;

    private String nickName;

    private String sex;

    private String email;

    private String status;


}

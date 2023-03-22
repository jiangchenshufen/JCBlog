package com.jiangchen.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    //邮箱
    private String email;
    //昵称
    private String nickName;
    //用户名
    private String userName;
    //密码
    private String password;
}

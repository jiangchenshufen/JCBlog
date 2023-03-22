package com.jiangchen.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDto {
    private Long id;
    //昵称
    private String nickName;
    //用户性别（0男，1女，2未知）
    private String sex;
    //邮箱
    private String email;
    //头像
    private String avatar;
}

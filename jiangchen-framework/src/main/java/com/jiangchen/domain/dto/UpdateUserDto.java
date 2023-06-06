package com.jiangchen.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    private Long id;

    private String userName;

    private String nickName;

    private String sex;

    private String email;

    private String status;

    private List<Long> roleIds;

}

package com.jiangchen.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {
    //tag标签
    private String name;

    //tag备注
    private String remark;
}

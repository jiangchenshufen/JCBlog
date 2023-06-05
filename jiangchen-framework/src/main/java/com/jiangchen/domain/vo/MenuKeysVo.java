package com.jiangchen.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuKeysVo {

    private List<MenuTreeVo> menus;

    private List<Long> checkedKeys;
}

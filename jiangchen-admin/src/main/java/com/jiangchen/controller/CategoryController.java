package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation("查询所有分类接口")
    @GetMapping("listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }
}

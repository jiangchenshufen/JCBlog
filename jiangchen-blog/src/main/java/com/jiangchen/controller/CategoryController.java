package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }

}

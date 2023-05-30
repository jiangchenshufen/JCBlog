package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateCategoryDto;
import com.jiangchen.domain.dto.addCategoryDto;
import com.jiangchen.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("分类列表")
    @GetMapping("/list")
    public ResponseResult List(Integer pageNum, Integer pageSize,String name,String status){
        return categoryService.showCategoryList(pageNum,pageSize,name,status);
    }

    @ApiOperation("添加分类")
    @PostMapping
    public ResponseResult addCategory(@RequestBody addCategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
    }

    @ApiOperation("根据id查询分类")
    @GetMapping("{id}")
    public ResponseResult selectCategoryById(@PathVariable("id") Long id){
        return categoryService.selectCategoryById(id);
    }


    @ApiOperation("修改分类")
    @PutMapping
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto){
        return categoryService.updateCategory(updateCategoryDto);
    }
}

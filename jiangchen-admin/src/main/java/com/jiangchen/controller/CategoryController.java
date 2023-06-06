package com.jiangchen.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateCategoryDto;
import com.jiangchen.domain.dto.addCategoryDto;
import com.jiangchen.domain.vo.ExcelCategoryVo;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.service.CategoryService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

    @ApiOperation("删除分类")
    @DeleteMapping({"{id}"})
    public ResponseResult delCategoryById(@PathVariable("id") Long id){
        return categoryService.delCategoryById(id);
    }

    @PreAuthorize("ps.hasPermissions('content:category:export')")
    @ApiOperation("导出分类列表")
    @GetMapping("/export")
    public void exportCategory(HttpServletResponse response){
        try {
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryService.list(), ExcelCategoryVo.class);
            EasyExcelFactory.write(response.getOutputStream(),ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出").doWrite(excelCategoryVos);
        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}

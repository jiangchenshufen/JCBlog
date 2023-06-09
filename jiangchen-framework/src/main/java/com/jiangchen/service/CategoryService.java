package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateCategoryDto;
import com.jiangchen.domain.dto.addCategoryDto;
import com.jiangchen.domain.entity.Category;


/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2023-01-05 10:00:38
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    /**
     * 获取全部分类
     * @return
     */
    ResponseResult listAllCategory();

    /**
     * 分类管理分页显示
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    ResponseResult showCategoryList(Integer pageNum, Integer pageSize, String name, String status);

    /**
     * 新增文章分类
      * @param addCategoryDto
     * @return
     */
    ResponseResult addCategory(addCategoryDto addCategoryDto);

    /**
     * 修改分类信息
     * @param updateCategoryDto
     * @return
     */
    ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto);

    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    ResponseResult selectCategoryById(Long id);

    /**
     * 根据id删除分类
     * @return
     */
    ResponseResult delCategoryById(Long id);
}

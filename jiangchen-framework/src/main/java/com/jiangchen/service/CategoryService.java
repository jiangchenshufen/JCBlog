package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
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
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult showCategoryList(Integer pageNum, Integer pageSize);
}

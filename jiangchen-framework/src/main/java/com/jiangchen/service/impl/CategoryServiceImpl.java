package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.UpdateCategoryDto;
import com.jiangchen.domain.dto.addCategoryDto;
import com.jiangchen.domain.entity.Article;
import com.jiangchen.domain.entity.Category;
import com.jiangchen.domain.vo.*;
import com.jiangchen.mapper.CategoryMapper;
import com.jiangchen.service.ArticleService;
import com.jiangchen.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jiangchen.service.CategoryService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2023-01-05 10:00:39
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult<ContentCategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,SystemConstants.NORMAL);
        List<ContentCategoryVo> contentCategoryVos = BeanCopyUtils.copyBeanList(list(wrapper), ContentCategoryVo.class);
        return ResponseResult.okResult(contentCategoryVos);
    }

    @Override
    public ResponseResult showCategoryList(Integer pageNum, Integer pageSize) {
        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page);
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVo.class);
        return ResponseResult.okResult(new PageVo(categoryListVos,page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(addCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        if (!save(category)){
            throw new RuntimeException("新增分类失败");
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCategory(UpdateCategoryDto updateCategoryDto) {
        Category category = BeanCopyUtils.copyBean(updateCategoryDto, Category.class);
        if (!updateById(category)){
            throw new RuntimeException("修改失败");
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectCategoryById(Long id) {
        Category category = getBaseMapper().selectById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(category, CategoryInfoVo.class));
    }
}

package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.AddArticleDto;
import com.jiangchen.domain.entity.Article;
import com.jiangchen.domain.vo.ArticleShowVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    /**
     * 后台文章列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    ResponseResult articleAdminList(Integer pageNum, Integer pageSize, String title, String summary);

    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    ResponseResult delArticleById(Integer id);

    /**
     * 根据id查询文章详细
     * @param id
     * @return
     */
    ResponseResult selectArticleById(Integer id);

    /**
     * 更新文章
     * @param articleShowDto
     * @return
     */
    ResponseResult putArticle(ArticleShowVo articleShowDto);
}

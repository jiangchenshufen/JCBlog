package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.AddArticleDto;
import com.jiangchen.domain.entity.Article;
import com.jiangchen.domain.entity.ArticleTag;
import com.jiangchen.domain.entity.Category;
import com.jiangchen.domain.vo.*;
import com.jiangchen.mapper.ArticleMapper;
import com.jiangchen.mapper.ArticleTagMapper;
import com.jiangchen.service.ArticleService;
import com.jiangchen.service.ArticleTagService;
import com.jiangchen.service.CategoryService;
import com.jiangchen.utils.BeanCopyUtils;
import com.jiangchen.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Resource
    private RedisCache redisCache;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        //修改文章浏览量从redis中获取
        articles.stream().forEach(article -> {
            Integer value = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEWS, article.getId().toString());
            article.setViewCount(value.longValue());
        });
        //bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //修改文章浏览量从redis中获取
        articles.stream().forEach(article -> {
            Integer value = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEWS, article.getId().toString());
            article.setViewCount(value.longValue());
        });
        //查询categoryName
        List<Article> collect = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(collect, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 获取文章详细信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询article列表
        Article article = getById(id);
        //查询redis的文章浏览量
        Integer newViewCount = redisCache.getCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEWS, id.toString());
        article.setViewCount(newViewCount.longValue());
        //返回vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据categoryId查询categoryName
        Category category = categoryService.getById(articleDetailVo.getCategoryId());
        if (!ObjectUtils.isEmpty(category)) {
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
         //更新redis中对应id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEWS,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> collect = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult articleAdminList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(title),Article::getTitle,title);
        wrapper.eq(!ObjectUtils.isEmpty(summary),Article::getSummary,summary);
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum).setSize(pageSize);
        page(page,wrapper);
        List<ArticleAdminListVo> articleAdminListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleAdminListVo.class);
        return ResponseResult.okResult(new PageVo(articleAdminListVos,page.getTotal()));
    }

    @Override
    public ResponseResult delArticleById(Integer id) {
        if (!(getBaseMapper().deleteById(id)>0)){
            throw new RuntimeException("删除失败");
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectArticleById(Integer id) {
        Article article = getBaseMapper().selectById(id);
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> ArticleTags = articleTagService.list(wrapper);
        List<Long> tags = ArticleTags.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());
        ArticleShowVo articleShowVo = BeanCopyUtils.copyBean(article, ArticleShowVo.class);
        articleShowVo.setTags(tags);
        return ResponseResult.okResult(articleShowVo);
    }

    @Override
    public ResponseResult putArticle(ArticleShowVo articleShowDto) {
        Article article = BeanCopyUtils.copyBean(articleShowDto, Article.class);
        if (!updateById(article)) throw new RuntimeException("文章保存失败");
        ArrayList<ArticleTag> articleTags = new ArrayList<>();
        articleShowDto.getTags().forEach(tagId -> articleTags.add(new ArticleTag(articleShowDto.getId(), tagId)));
        //if (!articleTagService.saveOrUpdateBatch(articleTags)) throw new RuntimeException("Tags保存失败");
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,articleShowDto.getId());
        articleTagMapper.delete(queryWrapper);
        if (!articleTagService.saveBatch(articleTags)) throw new RuntimeException("Tags保存失败");
        return ResponseResult.okResult();
    }
}



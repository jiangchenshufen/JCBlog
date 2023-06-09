package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.AddArticleDto;
import com.jiangchen.domain.vo.ArticleShowVo;
import com.jiangchen.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping()
    @ApiOperation("编写文章")
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }

    @ApiOperation("文章列表")
    @GetMapping("/list")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, String title, String summary){
        return articleService.articleAdminList(pageNum,pageSize,title,summary);
    }

    @ApiOperation("删除文章")
    @DeleteMapping("{id}")
    public ResponseResult delArticleById(@PathVariable("id") Integer id){
        return articleService.delArticleById(id);
    }

    @ApiOperation("根据id查询文章详情")
    @GetMapping("{id}")
    public ResponseResult selectArticleById(@PathVariable("id") Integer id){
        return articleService.selectArticleById(id);
    }

    @ApiOperation("更新文章")
    @PutMapping()
    public ResponseResult putArticle(@RequestBody ArticleShowVo articleShowDto){
        return articleService.putArticle(articleShowDto);
    }

}

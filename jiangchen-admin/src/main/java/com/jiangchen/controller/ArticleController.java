package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.AddArticleDto;
import com.jiangchen.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

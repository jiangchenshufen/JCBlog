package com.jiangchen.runner;

import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.entity.Article;
import com.jiangchen.service.ArticleService;
import com.jiangchen.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private ArticleService articleService;
    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        List<Article> list = articleService.list();
        Map<String, Integer> viewCountMap = list.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()));
        //存储到redis
        redisCache.setCacheMap(SystemConstants.REDIS_ARTICLE_VIEWS,viewCountMap);
    }
}

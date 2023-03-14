package com.jiangchen.utils;

import com.jiangchen.domain.entity.Article;
import com.jiangchen.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }


    @SuppressWarnings("unchecked")
    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }


//    public static void main(String[] args) {
//
//        Article article = new Article();
//        article.setId(1L);
//        article.setViewCount(99L);
//        HotArticleVo articleVo = copyBean(article, HotArticleVo.class);
//        System.out.println(articleVo);
//    }

}

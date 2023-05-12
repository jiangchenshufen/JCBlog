package com.jiangchen.constants;

public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 正常发布状态String
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链审核通过状态
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 友链正常状态
     */
    public static final String LINK_DEL_NORMAL = "0";

    /**
     * 登录验证token
     */
    public static final String LOGIN_AUTHENTICATION_TOKEN = "token";

    /**
     * redis前台登录前缀
     */
    public static final String REDIS_LOGIN_PREFIX = "blog_login:";
    /**
     * 类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 类型为：友链评论
     */
    public static final String LINK_COMMENT = "1";
    /**
     * redis文章浏览量
     */
    public static  final  String REDIS_ARTICLE_VIEWS = "article:viewCount";
    /**
     * redis后台登录前缀
     */
    public static final String REDIS_ADMIN_LOGIN_PREFIX = "admin_login:";
    /**
     * menu菜单
     */
    public static final String MENU = "C";
    /**
     * menu按钮
     */
    public static final String BUTTON = "F";
    /**
     * 角色权限字符
     */
    public static final String ROLE_KEY = "admin";
}



package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 15:09:40
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    /**
     * 友链分页查询
     * 模糊搜素
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    ResponseResult adminLinkList(Integer pageNum, Integer pageSize, String name, String status);
}

package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.LinkAddDto;
import com.jiangchen.domain.entity.Link;
import com.jiangchen.domain.vo.LinkAdminVo;


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

    /**
     * 新增友链
     * @param linkAddDto
     * @return
     */
    ResponseResult addLink(LinkAddDto linkAddDto);

    /**
     * 根据id查询友链信息
     * @param id
     * @return
     */
    ResponseResult selectLinkById(Long id);

    /**
     * 修改友链信息
     * @param linkAdminDto
     * @return
     */
    ResponseResult updateLink(LinkAdminVo linkAdminDto);

    /**
     * 根据id删除友链
     * @param id
     * @return
     */
    ResponseResult delLinkById(Long id);
}

package com.jiangchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.TagListDto;
import com.jiangchen.domain.entity.Tag;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.domain.vo.TagListVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-05-11 13:15:37
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 新增标签
     * @param tagListDto
     * @return
     */
    ResponseResult addTag(TagListDto tagListDto);

    /**
     * 根据标签id删除
     * @param id
     * @return
     */
    ResponseResult deleteTagById(Long id);

    /**
     * 根据id获取标签信息
     * @param id
     * @return
     */
    ResponseResult getTagInfo(Long id);

    /**
     * 修改标签信息
     * @param tagListVo
     * @return
     */
    ResponseResult updateTagInfo(TagListVo tagListVo);

    /**
     * 获取所有标签
     * @return
     */
    ResponseResult listAllTag();
}


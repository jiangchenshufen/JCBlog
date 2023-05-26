package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.TagListDto;
import com.jiangchen.domain.entity.Tag;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.domain.vo.TagListVo;
import com.jiangchen.mapper.TagMapper;
import com.jiangchen.service.TagService;
import com.jiangchen.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-11 13:15:37
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!ObjectUtils.isEmpty(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.eq(!ObjectUtils.isEmpty(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);
        //封装
        List<TagListVo> tagListVo = BeanCopyUtils.copyBeanList(page.getRecords(), TagListVo.class);
        PageVo pageVo = new PageVo( tagListVo, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}


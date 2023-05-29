package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.TagListDto;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.domain.vo.TagListVo;
import com.jiangchen.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @ApiOperation("查询标签")
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @ApiOperation("新增标签")
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @ApiOperation("删除标签")
    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTagById(id);
    }

    @ApiOperation("根据id获取标签信息")
    @GetMapping("{id}")
    public ResponseResult getTagInfo(@PathVariable("id") Long id){
        return tagService.getTagInfo(id);
    }

    @ApiOperation("修改标签信息")
    @PutMapping()
    public ResponseResult updateTagInfo(@RequestBody TagListVo tagListVo){
        return tagService.updateTagInfo(tagListVo);
    }
    @ApiOperation("获取所有标签")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

}

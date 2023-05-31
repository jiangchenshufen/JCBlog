package com.jiangchen.controller;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.dto.LinkAddDto;
import com.jiangchen.service.LinkService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @ApiOperation("显示友链列表")
    @GetMapping("/list")
    public ResponseResult showLinkList(Integer pageNum, Integer pageSize, String name, String status){
        return linkService.adminLinkList(pageNum, pageSize, name, status);
    }

    @ApiOperation("新增友链")
    @PostMapping()
    public ResponseResult addLink(@RequestBody LinkAddDto linkAddDto){
        return linkService.addLink(linkAddDto);
    }

}

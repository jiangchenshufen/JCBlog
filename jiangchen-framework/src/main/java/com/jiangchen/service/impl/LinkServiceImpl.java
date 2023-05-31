package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.constants.SystemConstants;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.Link;
import com.jiangchen.domain.vo.LinkAdminVo;
import com.jiangchen.domain.vo.LinkVo;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.mapper.LinkMapper;
import com.jiangchen.service.LinkService;
import com.jiangchen.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 15:09:40
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    /**
     * 友链查询所有
     * @return
     */
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL)
                .eq(Link::getDelFlag,SystemConstants.LINK_DEL_NORMAL);
        List<Link> list = list(wrapper);
        //转换Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult adminLinkList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotNull(name),Link::getName,name);
        wrapper.eq(ObjectUtils.isNotNull(status),Link::getStatus,status);
        Page<Link> linkPage = new Page<>();
        linkPage.setCurrent(pageNum).setSize(pageSize);
        page(linkPage,wrapper);
        List<LinkAdminVo> linkAdminVos = BeanCopyUtils.copyBeanList(linkPage.getRecords(), LinkAdminVo.class);
        return ResponseResult.okResult(new PageVo(linkAdminVos, linkPage.getTotal()));
    }
}

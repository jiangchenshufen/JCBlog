package com.jiangchen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiangchen.domain.ResponseResult;
import com.jiangchen.domain.entity.Comment;
import com.jiangchen.domain.vo.CommentVo;
import com.jiangchen.domain.vo.PageVo;
import com.jiangchen.mapper.CommentMapper;
import com.jiangchen.service.CommentService;
import com.jiangchen.service.UserService;
import com.jiangchen.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;


/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-15 15:13:49
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    public static final Long CONSTANT_MINUS_ONE = -1L;

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章根评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        wrapper.eq(Comment::getArticleId, articleId)
                .eq(Comment::getRootId, CONSTANT_MINUS_ONE);
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        //封装响应Vo
        List<CommentVo> commentVos = toCommentVoLists(page.getRecords());
        //查询所有根评论的子评论
        commentVos.stream().forEach(commentVo -> {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        });
        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    /**
     * 根据根评论id查询所有子评论
     * @param id
     * @return
     */
    private List<CommentVo> getChildren(Long id){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId,id).orderByAsc(Comment::getCreateTime);
        List<Comment> commentList = list(wrapper);
        List<CommentVo> commentVoList = toCommentVoLists(commentList);
        return commentVoList;
    }

    private List<CommentVo> toCommentVoLists(List<Comment> list) {
        List<CommentVo> commentVoLists = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVoLists.stream().forEach(commentVo -> {
            //通过createBy查询昵称
            String userName = userService.getById(commentVo.getCreateBy()).getUserName();
            commentVo.setUsername(userName);
            if (commentVo.getToCommentUserId() != CONSTANT_MINUS_ONE){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        });
        return commentVoLists;
    }

}

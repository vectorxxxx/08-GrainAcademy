package xyz.funnyboy.eduservice.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.JwtUtils;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.client.UcenterClient;
import xyz.funnyboy.eduservice.entity.EduComment;
import xyz.funnyboy.eduservice.service.EduCommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2023-12-31
 */
@Api(description = "评论管理")
// @CrossOrigin
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController
{
    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    @ApiOperation(value = "评论分页列表")
    @GetMapping("{page}/{limit}")
    public R list(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    long limit,

            @ApiParam(name = "courseId",
                      value = "课程id",
                      required = true)
            @RequestParam
                    String courseId) {
        final Page<EduComment> pageParam = new Page<>(page, limit);
        final LambdaQueryWrapper<EduComment> queryWrapper = new LambdaQueryWrapper<EduComment>().eq(EduComment::getCourseId, courseId);
        commentService.page(pageParam, queryWrapper);

        return R.ok()
                .data("total", pageParam.getTotal())
                .data("current", pageParam.getCurrent())
                .data("pages", pageParam.getPages())
                .data("size", pageParam.getSize())
                .data("hasPrevious", pageParam.hasPrevious())
                .data("hasNext", pageParam.hasNext())
                .data("items", pageParam.getRecords());
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("")
    public R addComment(
            @ApiParam(name = "comment",
                      value = "评论信息",
                      required = true)
            @RequestBody
                    EduComment comment, HttpServletRequest request) {
        final String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error()
                    .message("请先登录");
        }

        // 获取用户信息
        final R info = ucenterClient.getInfo(memberId);
        if (!info.getSuccess()) {
            return R.error()
                    .message("查询用户信息失败");
        }

        final Map<String, Object> data = info.getData();
        final String nickname = (String) data.get("nickname");
        final String avatar = (String) data.get("avatar");

        // 设置用户头像和昵称
        comment.setNickname(nickname)
               .setAvatar(avatar);
        commentService.save(comment);
        return R.ok();
    }
}


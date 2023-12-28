package xyz.funnyboy.eduservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.eduservice.entity.vo.VideoVO;
import xyz.funnyboy.eduservice.service.EduVideoService;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2023-12-26
 */
@Api(description = "课时管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController
{
    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation(value = "添加课时")
    @PostMapping("")
    public R addVideo(
            @ApiParam(name = "video",
                      value = "课时信息",
                      required = true)
            @RequestBody
                    VideoVO videoVO) {
        eduVideoService.saveVideoVO(videoVO);
        return R.ok();
    }

    @ApiOperation(value = "根据id获取课时信息")
    @GetMapping("{id}")
    public R getVideo(
            @ApiParam(name = "id",
                      value = "课时id",
                      required = true)
            @PathVariable
                    String id) {
        VideoVO videoVO = eduVideoService.getVideoVOById(id);
        return R.ok()
                .data("item", videoVO);
    }

    @ApiOperation(value = "根据id删除课时")
    @DeleteMapping("{id}")
    public R removeVideo(
            @ApiParam(name = "id",
                      value = "课时id",
                      required = true)
            @PathVariable
                    String id) {
        eduVideoService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("{id}")
    public R updateVideo(
            @ApiParam(name = "id",
                      value = "课时id",
                      required = true)
            @PathVariable
                    String id,
            @ApiParam(name = "video",
                      value = "课时信息",
                      required = true)
            @RequestBody
                    VideoVO videoVO) {
        videoVO.setId(id);
        eduVideoService.updateVideoVO(videoVO);
        return R.ok();
    }
}


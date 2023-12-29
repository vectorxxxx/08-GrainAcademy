package xyz.funnyboy.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.vod.service.VodService;

import java.util.List;

/**
 * VodController
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 00:28:44
 */
@Api(description = "阿里云视频点播服务")
@RestController
@RequestMapping("/vod")
@CrossOrigin
public class VodController
{
    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频")
    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file",
                      value = "视频文件",
                      required = true)
                    MultipartFile file) {
        final String videoId = vodService.uploadVideo(file);
        return R.ok()
                .message("视频上传成功")
                .data("videoId", videoId);
    }

    @ApiOperation(value = "删除视频")
    @DeleteMapping("{videoId}")
    public R removeVideo(
            @ApiParam(name = "videoId",
                      value = "视频ID",
                      required = true)
            @PathVariable
                    String videoId) {
        vodService.removeVideo(videoId);
        return R.ok()
                .message("视频删除成功");
    }

    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("")
    public R removeVideoList(
            @ApiParam(name = "videoIdList",
                      value = "视频ID列表",
                      required = true)
            @RequestParam("videoIdList")
                    List<String> videoIdList) {
        vodService.removeVideoList(videoIdList);
        return R.ok()
                .message("视频删除成功");
    }
}

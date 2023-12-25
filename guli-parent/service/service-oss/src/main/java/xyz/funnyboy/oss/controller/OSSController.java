package xyz.funnyboy.oss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.oss.service.OSSService;

/**
 * OSSController
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-25 21:57:43
 */
@Api(value = "阿里云文件管理")
@RestController
@RequestMapping("/oss")
@CrossOrigin
public class OSSController
{
    @Autowired
    private OSSService ossService;

    @ApiOperation(value = "上传头像")
    @PostMapping("/upload/avatar")
    public R uploadAvatar(
            @ApiParam(name = "file",
                      value = "头像文件",
                      required = true)
            @RequestParam("file")
                    MultipartFile file) {
        final String url = ossService.uploadAvatar(file);
        return R.ok()
                .message("头像上传成功")
                .data("url", url);
    }
}

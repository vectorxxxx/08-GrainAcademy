package xyz.funnyboy.eduservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;

/**
 * 登录接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-24 16:42:41
 */
@Api(description = "登录接口")
@RestController
@RequestMapping("/eduservice/user")
// @CrossOrigin
public class EduLoginController
{
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login() {
        return R.ok()
                .data("token", "admin");
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin");
    }
}

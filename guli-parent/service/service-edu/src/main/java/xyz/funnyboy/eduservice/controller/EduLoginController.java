package xyz.funnyboy.eduservice.controller;

import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;

/**
 * 登录接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-24 16:42:41
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController
{
    @PostMapping("login")
    public R login() {
        return R.ok()
                .data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin");
    }
}

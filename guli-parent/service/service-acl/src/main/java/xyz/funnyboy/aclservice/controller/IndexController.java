package xyz.funnyboy.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.aclservice.service.IndexService;
import xyz.funnyboy.commonutils.R;

import java.util.List;
import java.util.Map;

/**
 * IndexController
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-03 17:49:11
 */
@Api(description = "首页接口")
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController
{
    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R info() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext()
                                               .getAuthentication()
                                               .getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok()
                .data(userInfo);
    }

    /**
     * 获取菜单
     *
     * @return
     */
    @GetMapping("menu")
    public R getMenu() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext()
                                               .getAuthentication()
                                               .getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok()
                .data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }
}

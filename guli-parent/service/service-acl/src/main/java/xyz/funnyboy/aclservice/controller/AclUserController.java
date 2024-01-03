package xyz.funnyboy.aclservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.aclservice.entity.AclUser;
import xyz.funnyboy.aclservice.service.AclRoleService;
import xyz.funnyboy.aclservice.service.AclUserService;
import xyz.funnyboy.commonutils.MD5;
import xyz.funnyboy.commonutils.R;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@RestController
@RequestMapping("/admin/acl/user")
public class AclUserController
{
    @Autowired
    private AclUserService userService;

    @Autowired
    private AclRoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    Long limit,

            @ApiParam(name = "searchObj",
                      value = "查询对象",
                      required = false)
                    AclUser searchObj) {
        Page<AclUser> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<AclUser> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(searchObj.getUsername())) {
            wrapper.like(AclUser::getUsername, searchObj.getUsername());
        }

        IPage<AclUser> pageModel = userService.page(pageParam, wrapper);
        return R.ok()
                .data("items", pageModel.getRecords())
                .data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public R save(
            @ApiParam(name = "user",
                      value = "用户",
                      required = true)
            @RequestBody
                    AclUser user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return R.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public R updateById(
            @ApiParam(name = "user",
                      value = "用户",
                      required = true)
            @RequestBody
                    AclUser user) {
        userService.updateById(user);
        return R.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public R remove(
            @ApiParam(name = "id",
                      value = "用户id",
                      required = true)
            @PathVariable
                    String id) {
        userService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public R batchRemove(
            @ApiParam(name = "idList",
                      value = "用户id集合",
                      required = true)
            @RequestBody
                    List<String> idList) {
        userService.removeByIds(idList);
        return R.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(
            @PathVariable
                    String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok()
                .data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(
            @RequestParam
                    String userId,
            @RequestParam
                    String[] roleId) {
        roleService.saveUserRoleRelationShip(userId, roleId);
        return R.ok();
    }
}


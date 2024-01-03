package xyz.funnyboy.aclservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.aclservice.entity.AclRole;
import xyz.funnyboy.aclservice.service.AclRoleService;
import xyz.funnyboy.commonutils.R;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@Api(description = "角色管理")
@RestController
@RequestMapping("/admin/acl/role")
public class AclRoleController
{
    @Autowired
    private AclRoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
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

            @ApiParam(name = "role",
                      value = "查询条件",
                      required = false)
                    AclRole role) {
        Page<AclRole> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<AclRole> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like(AclRole::getRoleName, role.getRoleName());
        }
        roleService.page(pageParam, wrapper);
        return R.ok()
                .data("items", pageParam.getRecords())
                .data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public R get(
            @ApiParam(name = "id",
                      value = "角色id",
                      required = true)
            @PathVariable
                    String id) {
        AclRole role = roleService.getById(id);
        return R.ok()
                .data("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public R save(
            @ApiParam(name = "role",
                      value = "角色",
                      required = true)
            @RequestBody
                    AclRole role) {
        roleService.save(role);
        return R.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public R updateById(
            @ApiParam(name = "role",
                      value = "角色",
                      required = true)
            @RequestBody
                    AclRole role) {
        roleService.updateById(role);
        return R.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public R remove(
            @ApiParam(name = "id",
                      value = "角色id",
                      required = true)
            @PathVariable
                    String id) {
        roleService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public R batchRemove(
            @ApiParam(name = "idList",
                      value = "角色id集合",
                      required = true)
            @RequestBody
                    List<String> idList) {
        roleService.removeByIds(idList);
        return R.ok();
    }
}


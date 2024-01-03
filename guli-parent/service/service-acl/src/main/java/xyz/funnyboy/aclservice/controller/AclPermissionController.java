package xyz.funnyboy.aclservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.aclservice.entity.AclPermission;
import xyz.funnyboy.aclservice.service.AclPermissionService;
import xyz.funnyboy.commonutils.R;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@Api(description = "权限管理")
@RestController
@RequestMapping("/admin/acl/permission")
public class AclPermissionController
{
    @Autowired
    private AclPermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public R indexAllPermission() {
        List<AclPermission> list = permissionService.queryAllMenu();
        return R.ok()
                .data("children", list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(
            @ApiParam(name = "id",
                      value = "菜单id",
                      required = true)
            @PathVariable
                    String id) {
        permissionService.removeChildById(id);
        return R.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId, String[] permissionId) {
        permissionService.saveRolePermissionRelationShip(roleId, permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(
            @ApiParam(name = "roleId",
                      value = "角色id",
                      required = true)
            @PathVariable
                    String roleId) {
        List<AclPermission> list = permissionService.selectAllMenu(roleId);
        return R.ok()
                .data("children", list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(
            @ApiParam(name = "permission",
                      value = "权限",
                      required = true)
            @RequestBody
                    AclPermission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(
            @ApiParam(name = "permission",
                      value = "权限",
                      required = true)
            @RequestBody
                    AclPermission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}


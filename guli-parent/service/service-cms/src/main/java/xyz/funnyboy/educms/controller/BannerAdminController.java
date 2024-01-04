package xyz.funnyboy.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.educms.entity.CrmBanner;
import xyz.funnyboy.educms.service.CrmBannerService;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author VectorX
 * @since 2023-12-29
 */
@Api(description = "轮播图管理")
@RestController
@RequestMapping("/educms/banneradmin")
// @CrossOrigin
public class BannerAdminController
{
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page",
                      value = "当前页数",
                      required = true)
            @PathVariable("page")
                    Integer page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable("limit")
                    Integer limit) {
        final Page<CrmBanner> pageParam = new Page<>(page, limit);
        crmBannerService.page(pageParam, null);
        return R.ok()
                .data("total", pageParam.getTotal())
                .data("rows", pageParam.getRecords());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id",
                      value = "唯一标识",
                      required = true)
            @PathVariable("id")
                    String id) {
        final CrmBanner banner = crmBannerService.getById(id);
        return R.ok()
                .data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping
    public R save(
            @ApiParam(name = "banner",
                      value = "轮播图",
                      required = true)
            @RequestBody
                    CrmBanner banner) {
        crmBannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping
    public R update(
            @ApiParam(name = "banner",
                      value = "轮播图",
                      required = true)
            @RequestBody
                    CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id",
                      value = "唯一标识",
                      required = true)
            @PathVariable("id")
                    String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }
}


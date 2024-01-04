package xyz.funnyboy.educms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.commonutils.R;
import xyz.funnyboy.educms.entity.CrmBanner;
import xyz.funnyboy.educms.service.CrmBannerService;

import java.util.List;

/**
 * 网站首页Banner列表
 *
 * @author VectorX
 * @version V1.0
 * @date 2023-12-29 21:38:00
 */
@Api(description = "网站首页Banner列表")
@RestController
@RequestMapping("/educms/bannerfront")
// @CrossOrigin
public class BannerFrontController
{
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation(value = "查询所有banner")
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok()
                .data("bannerList", list);
    }
}

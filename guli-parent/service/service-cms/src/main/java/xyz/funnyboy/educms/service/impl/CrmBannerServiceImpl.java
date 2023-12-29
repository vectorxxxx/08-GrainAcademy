package xyz.funnyboy.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.educms.entity.CrmBanner;
import xyz.funnyboy.educms.mapper.CrmBannerMapper;
import xyz.funnyboy.educms.service.CrmBannerService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2023-12-29
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService
{

    /**
     * 获取首页Banner
     *
     * @return {@link List}<{@link CrmBanner}>
     */
    @Override
    public List<CrmBanner> selectAllBanner() {
        final LambdaQueryWrapper<CrmBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(CrmBanner::getId)
                    .last("limit 2");
        return this.list(queryWrapper);
    }
}

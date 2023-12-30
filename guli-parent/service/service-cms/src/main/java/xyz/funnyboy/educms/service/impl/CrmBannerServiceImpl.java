package xyz.funnyboy.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.funnyboy.educms.entity.CrmBanner;
import xyz.funnyboy.educms.mapper.CrmBannerMapper;
import xyz.funnyboy.educms.service.CrmBannerService;

import java.io.Serializable;
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
    @SuppressWarnings("unchecked")
    @Cacheable(value = "banner",
               key = "'selectAllBanner'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        final LambdaQueryWrapper<CrmBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                // 排序
                .orderByDesc(CrmBanner::getSort)
                // 只获取前两条
                .last("limit 2");
        return this.list(queryWrapper);
    }

    @CacheEvict(value = "banner",
                allEntries = true)
    @Override
    public boolean save(CrmBanner entity) {
        return super.save(entity);
    }

    @CacheEvict(value = "banner",
                allEntries = true)
    @Override
    public boolean updateById(CrmBanner entity) {
        return super.updateById(entity);
    }

    @CacheEvict(value = "banner",
                allEntries = true)
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}

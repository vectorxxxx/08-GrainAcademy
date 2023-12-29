package xyz.funnyboy.educms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.educms.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2023-12-29
 */
public interface CrmBannerService extends IService<CrmBanner>
{

    /**
     * 获取首页Banner
     *
     * @return {@link List}<{@link CrmBanner}>
     */
    List<CrmBanner> selectAllBanner();
}

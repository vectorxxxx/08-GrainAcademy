package xyz.funnyboy.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.aclservice.entity.AclUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
public interface AclUserService extends IService<AclUser>
{

    AclUser selectByUsername(String username);
}

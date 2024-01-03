package xyz.funnyboy.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.aclservice.entity.AclUser;
import xyz.funnyboy.aclservice.mapper.AclUserMapper;
import xyz.funnyboy.aclservice.service.AclUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author VectorX
 * @since 2024-01-03
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements AclUserService
{

    @Override
    public AclUser selectByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<AclUser>().eq(AclUser::getUsername, username));
    }
}

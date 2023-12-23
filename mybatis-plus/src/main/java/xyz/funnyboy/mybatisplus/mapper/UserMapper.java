package xyz.funnyboy.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xyz.funnyboy.mybatisplus.entity.User;

/**
 * @author VectorX
 * @version V1.0
 * @description 用户Mapper
 * @date 19/12/2023
 */
@Repository
public interface UserMapper extends BaseMapper<User>
{
}

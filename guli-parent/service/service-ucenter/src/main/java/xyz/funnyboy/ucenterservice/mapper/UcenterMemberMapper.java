package xyz.funnyboy.ucenterservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.ucenterservice.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author VectorX
 * @since 2023-12-30
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember>
{

    /**
     * 查询某天的注册人数
     *
     * @param day 日期
     * @return int
     */
    int selectRegisterCount(String day);
}

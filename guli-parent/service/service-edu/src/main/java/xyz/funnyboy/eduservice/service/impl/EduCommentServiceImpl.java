package xyz.funnyboy.eduservice.service.impl;

import xyz.funnyboy.eduservice.entity.EduComment;
import xyz.funnyboy.eduservice.mapper.EduCommentMapper;
import xyz.funnyboy.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2023-12-31
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

}

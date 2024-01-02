package xyz.funnyboy.orderservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.commonutils.vo.CoursePublishVO;

/**
 * 课程远程接口失败调用类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-01 23:37:24
 */
@Component
public class CourseClientFallback implements CourseClient
{
    @Override
    public CoursePublishVO getCourseInfoRemote(
            @PathVariable
                    String courseId) {
        return null;
    }
}

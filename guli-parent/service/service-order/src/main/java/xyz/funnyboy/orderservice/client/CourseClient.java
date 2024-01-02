package xyz.funnyboy.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.commonutils.vo.CoursePublishVO;

/**
 * 课程远程接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-01 23:34:58
 */
@FeignClient(name = "service-edu",
             fallback = CourseClientFallback.class)
@Component
public interface CourseClient
{
    @GetMapping("/eduservice/course/getCourseInfo/remote/{courseId}")
    CoursePublishVO getCourseInfoRemote(
            @PathVariable("courseId")
                    String courseId);
}

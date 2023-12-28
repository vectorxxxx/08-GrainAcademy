import request from '@/utils/request'

const api_name = '/eduservice/course'

export default {
  // 保存课程信息
  saveCourseInfo(courseInfo) {
    return request({
      url: `${api_name}/addCourseInfo`,
      method: 'post',
      data: courseInfo
    });
  },
  // 根据ID获取课程信息
  getCourseInfoById(courseId) {
    return request({
      url: `${api_name}/getCourseInfo/${courseId}`,
      method: 'get'
    });
  },
  // 更新课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `${api_name}/updateCourseInfo`,
      method: 'put',
      data: courseInfo
    });
  },
  // 根据课程id获取课程基本预览信息
  getCoursePublishInfoById(courseId) {
    return request({
      url: `${api_name}/getCoursePublishInfo/${courseId}`,
      method: 'get'
    })
  },
  // 课程最终发布
  publishCourse(courseId) {
    return request({
      url: `${api_name}/publishCourse/${courseId}`,
      method: 'put'
    })
  }
};

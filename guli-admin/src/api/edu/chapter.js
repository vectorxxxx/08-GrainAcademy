import request from '@/utils/request'

const api_name = '/eduservice/chapter'

export default {
  // 获取章节课时信息
  getChapterVideo(courseId) {
    return request({
      url: `${api_name}/getChapterVideo/${courseId}`,
      method: 'get'
    });
  },
  // 获取章节
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    });
  },
  // 修改章节
  updateById(chapter) {
    return request({
      url: `${api_name}/${chapter.id}`,
      method: 'put',
      data: chapter
    });
  },
  // 删除章节
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    });
  },
  // 添加章节
  save(chapter) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: chapter
    });
  }
};

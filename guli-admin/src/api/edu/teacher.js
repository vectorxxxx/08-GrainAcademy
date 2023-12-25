import request from '@/utils/request';

const api_name = '/eduservice/teacher';
export default {
  getPageList(page, limit, teacherQuery) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'post',
      data: teacherQuery
    });
  },
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    });
  },
  save(teacher) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: teacher
    })
  },
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  updateById(teacher) {
    return request({
      url: `${api_name}/${teacher.id}`,
      method: 'put',
      data: teacher
    })
  }
};

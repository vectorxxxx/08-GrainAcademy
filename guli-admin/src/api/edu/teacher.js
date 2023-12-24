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
  }
};

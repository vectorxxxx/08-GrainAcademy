import request from '@/utils/request';

const api_name = '/eduservice/teacher';
export default {
  // 讲师分页数据
  getPageList(page, limit, teacherQuery) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'post',
      data: teacherQuery
    });
  },
  // 根据ID删除讲师
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    });
  },
  // 保存讲师
  save(teacher) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: teacher
    });
  },
  // 根据ID获取讲师
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    });
  },
  // 根据ID更新讲师
  updateById(teacher) {
    return request({
      url: `${api_name}/${teacher.id}`,
      method: 'put',
      data: teacher
    });
  },
  // 获取所有讲师
  getList() {
    return request({
      url: `${api_name}`,
      method: 'get'
    })
  }
};

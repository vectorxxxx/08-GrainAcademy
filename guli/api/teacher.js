import request from '../utils/request'

const api_name = '/eduservice/teacher'

export default {
  // 分页查询讲师
  getPageList(page, limit) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'post'
    })
  },
  // 根据ID查询讲师
  getById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  }
}

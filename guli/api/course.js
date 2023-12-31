import request from '../utils/request'

export default {
  // 分页查询课程
  getPageList(page, limit, searchObj) {
    return request({
      url: `/eduservice/course/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  // 获取课程二级分类
  getNestedTreeList() {
    return request({
      url: `/eduservice/subject`,
      method: 'get'
    })
  },
  // 课程详情的方法
  getById(id) {
    return request({
      url: `/eduservice/course/${id}`,
      method: 'get'
    })
  }
}

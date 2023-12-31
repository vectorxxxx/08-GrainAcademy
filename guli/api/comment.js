import request from '../utils/request'

const api_name = '/eduservice/comment'

export default {
  // 评论分页列表
  getPageList(page, limit, courseId) {
    return request({
      url: `${api_name}/${page}/${limit}?courseId=` + courseId,
      method: 'get'
    })
  },
  // 添加评论
  addComment(comment) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: comment
    })
  }
}

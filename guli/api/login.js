import request from '@/utils/request'

export default {
  // 登录
  login(loginVO) {
    return request({
      url: '/educenter/member/login',
      method: 'post',
      data: loginVO
    })
  },
  // 根据token获取用户信息
  getInfo() {
    return request({
      url: '/educenter/member/info',
      method: 'get'
    })
  }
}

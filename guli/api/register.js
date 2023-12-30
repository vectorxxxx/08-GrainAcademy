import request from '../utils/request'

export default {
  // 根据手机号发送短信
  sendSms(phone) {
    return request({
      url: `/edusms/sms/send/${phone}`,
      method: 'get'
    })
  },
  // 注册
  register(registerVO) {
    return request({
      url: '/educenter/member/register',
      method: 'post',
      data: registerVO
    })
  }
}


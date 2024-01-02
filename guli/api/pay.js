import request from '../utils/request'

const pay_api = '/orderservice/paylog'

export default {
  // 创建支付日志
  createNative(orderNo) {
    return request({
      url: `${pay_api}/createNative/${orderNo}`,
      method: 'post'
    })
  },
  // 查询支付状态
  queryPayStatus(orderNo) {
    return request({
      url: `${pay_api}/queryPayStatus/${orderNo}`,
      method: 'get'
    })
  }
}


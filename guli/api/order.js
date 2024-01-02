import request from '../utils/request'

const order_api = '/orderservice/order'

export default {
  // 创建订单
  createOrder(courseId) {
    return request({
      url: `${order_api}/createOrder/${courseId}`,
      method: 'post'
    })
  },
  // 查询订单
  getOrder(orderNo) {
    return request({
      url: `${order_api}/getOrder/${orderNo}`,
      method: 'get'
    })
  }
}


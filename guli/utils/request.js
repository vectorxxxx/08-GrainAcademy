import axios from 'axios'
import { Message } from 'element-ui'
import cookie from 'js-cookie'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8160', // api的base_url
  timeout: 20000 // 请求超时时间
})

// http request 拦截器
service.interceptors.request.use(
  config => {
    if (cookie.get('guli_token')) {
      config.headers['token'] = cookie.get('guli_token')
    }
    return config
  },
  err => {
    console.log(err)
    return Promise.reject(err)
  }
)

// http response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data
    if (res.code !== 20000) {
      // 25000：订单支付中，不做任何提示
      if (response.data.code !== 25000) {
        Message({
          message: res.message || 'error',
          type: 'error',
          duration: 5 * 1000
        })
      }
      // return Promise.reject('error')
    } else {
      return response
    }
  },
  err => {
    console.log('err' + err)
    Message({
      message: err.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(err.response)
  }
)

export default service

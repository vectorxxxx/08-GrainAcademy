import request from '@/utils/request'

const api_name = '/admin/acl/index'

// 登录
export function login(username, password) {
  return request({
    url: '/admin/acl/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

// 获取用户信息
export function getInfo(token) {
  return request({
    url: `${api_name}/info`,
    method: 'get',
    params: { token }
  })
}

// 退出登录
export function logout() {
  return request({
    url: `${api_name}/logout`,
    method: 'post'
  })
}

export function getMenu() {
  return request({
    url: `${api_name}/menu`,
    method: 'get'
  })
}

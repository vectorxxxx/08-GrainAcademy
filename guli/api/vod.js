import request from '../utils/request'

const api_name = '/vod'

export default {
  getPlayAuth(vid) {
    return request({
      url: `${api_name}/getPlayAuth/${vid}`,
      method: 'get'
    })
  }
}

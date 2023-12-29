import request from '@/utils/request'

export default {
  // 获取所有轮播图
  getBannerList() {
    return request({
      url: `/educms/bannerfront/getAllBanner`,
      method: 'get'
    })
  }
}

import request from '../utils/request'

const api_name = '/statisticsservice/statistics'

export default {
  // 生成图表
  createStatisticsByDay(day) {
    return request({
      url: `${api_name}/${day}`,
      method: 'post'
    })
  },
  // 显示图表
  showChart(searchObj) {
    return request({
      url: `${api_name}/showChart/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    })
  }
}

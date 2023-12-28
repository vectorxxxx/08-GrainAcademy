import request from '@/utils/request';

const api_name = '/eduservice/video';

export default {
  // 添加小节
  addVideo(video) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: video
    });
  },
  // 删除小节
  removeById(videoId) {
    return request({
      url: `${api_name}/${videoId}`,
      method: 'delete'
    });
  },
  // 修改小节
  updateVideo(video) {
    return request({
      url: `${api_name}/${video.id}`,
      method: 'put',
      data: video
    });
  },
  // 根据id查询小节
  getById(videoId) {
    return request({
      url: `${api_name}/${videoId}`,
      method: 'get'
    });
  }
};

<template>
  <div>
    <!-- 阿里云视频播放器样式 -->
    <link
      rel="stylesheet"
      href="https://g.alicdn.com/apsara-media-box/imp-web-player/2.19.0/skins/default/aliplayer-min.css">
    <!-- 阿里云视频播放器脚本 -->
    <script
      type="text/javascript"
      charset="utf-8"
      src="https://g.alicdn.com/apsara-media-box/imp-web-player/2.19.0/aliplayer-min.js"/>

    <!-- 定义播放器dom -->
    <div id="player-con" class="prism-player"/>
  </div>
</template>

<script>
import vod from '@/api/vod'

export default {
  layout: 'video', // 应用video布局
  asyncData({ params, error }) {
    return vod.getPlayAuth(params.vid).then(response => {
      return {
        vid: params.vid,
        playAuth: response.data.data.playAuth
      }
    })
  },
  /**
   * 页面渲染完成时：此时js脚本已加载，Aliplayer已定义，可以使用
   * 如果在created生命周期函数中使用，Aliplayer is not defined错误
   */
  mounted() {
    // eslint-disable-next-line no-undef
    new Aliplayer({
      'id': 'player-con',
      'vid': this.vid,
      'playauth': this.playAuth,
      'qualitySort': 'asc',
      'format': 'mp4',
      'mediaType': 'video',
      'width': '100%',
      'height': '500px',
      'autoplay': false,
      'isLive': false,
      'rePlay': false,
      'playsinline': true,
      'preload': true,
      'controlBarVisibility': 'hover',
      'useH5Prism': true
    }, function(player) {
      console.log('The player is created')
    })
  }
}
</script>

<style scoped>

</style>

<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-button type="text" @click="addChapter()">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chapterList">
      <li
        v-for="chapter in chapterVideoList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="addVideo(chapter.id)">添加课时</el-button>
            <el-button type="text" @click="editChapter(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>
        <!-- 视频 -->
        <ul class="chapterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                <el-button type="text" @click="editVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible=false">取消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleVodUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/vod/upload'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible=false">取消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import chapter from '@/api/edu/chapter';
import video from '@/api/edu/video';
import vod from '@/api/edu/vod';

export default {
  data() {
    return {
      // chapter
      courseId: '', // 课程id
      chapterVideoList: [], // 章节嵌套列表
      dialogChapterFormVisible: false, // 章节表单是否显示
      chapter: { // 章节对象
        title: '',
        sort: 0
      },
      saveBtnDisabled: false, // 保存按钮是否禁用
      coursePublish: {},
      // video
      saveVideoBtnDisabled: false, // 保存按钮是否禁用
      dialogVideoFormVisible: false, // 视频表单是否显示
      chapterId: '', // 当前编辑章节id
      video: { // 视频对象
        title: '',
        sort: 0,
        free: false,
        videoSourceId: '',
        videoOriginalName: ''
      },
      // vod
      fileList: [], // 上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址
    };
  },

  created() {
    console.log('info created');
    this.init();
  },

  methods: {
    // 初始化
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id;
        this.fetchChapterVideoList();
      }
    },
    /** 章节操作 */
    // 获取章节课时列表
    fetchChapterVideoList() {
      chapter.getChapterVideo(this.courseId).then(response => {
        this.chapterVideoList = response.data.items;
      });
    },
    // 上一步
    previous() {
      console.log('previous');
      this.$router.push({ path: '/edu/course/info/' + this.courseId });
    },
    // 下一步
    next() {
      console.log('next');
      this.$router.push({ path: '/edu/course/publish/' + this.courseId });
    },
    // 添加
    addChapter() {
      this.dialogChapterFormVisible = true;
      this.chapter.title = '';
      this.chapter.sort = 0;
      this.saveBtnDisabled = false;
    },
    // 编辑
    editChapter(chapterId) {
      this.dialogChapterFormVisible = true;
      chapter.getById(chapterId).then(response => {
        this.chapter = response.data.item;
      }).catch(response => {
        this.$message({
          type: 'error',
          message: '获取章节信息失败'
        });
      });
    },
    // 删除
    removeChapter(chapterId) {
      this.$confirm('此操作将永久删除章节记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return chapter.removeById(chapterId);
      }).then(() => {
        this.fetchChapterVideoList();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch((response) => {
        if (response === 'cancel') {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        } else {
          this.$message({
            type: 'error',
            message: response.message
          });
        }
      });
    },
    // 确定
    saveOrUpdate() {
      this.saveBtnDisabled = true; // 防止表单重复提交
      if (!this.chapter.id) {
        this.saveData();
      } else {
        this.updateData();
      }
    },
    // 保存数据
    saveData() {
      this.chapter.courseId = this.courseId;
      chapter.save(this.chapter).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        });
        this.helpSave();
      }).catch(response => {
        this.$message({
          type: 'error',
          message: response.message
        });
      });
    },
    // 修改数据
    updateData() {
      chapter.updateById(this.chapter).then(response => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        });
        this.helpSave();
      }).catch(response => {
        this.$message({
          type: 'error',
          message: response.message
        });
      });
    },
    // 帮助保存
    helpSave() {
      this.dialogChapterFormVisible = false;
      this.fetchChapterVideoList();
      this.chapter.title = '';
      this.chapter.sort = 0;
      this.saveBtnDisabled = false;
    },

    /** 课时操作 */
    // 添加课时
    addVideo(chapterId) {
      this.dialogVideoFormVisible = true;
      this.chapterId = chapterId;
      this.video.title = '';
      this.video.sort = 0;
      this.video.free = false;
      this.video.videoSourceId = '';
    },
    // 保存或修改课时
    saveOrUpdateVideo() {
      this.saveVideoBtnDisabled = true;
      if (!this.video.id) {
        this.saveVideoData();
      } else {
        this.updateVideoData();
      }
    },
    // 保存课时
    saveVideoData() {
      this.video.courseId = this.courseId;
      this.video.chapterId = this.chapterId;
      video.addVideo(this.video).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        });
        this.helpSaveVideo();
      });
    },
    // 修改课时
    updateVideoData() {
      video.updateVideo(this.video).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        });
        this.helpSaveVideo();
      });
    },
    // 帮助保存
    helpSaveVideo() {
      this.dialogVideoFormVisible = false;
      this.fetchChapterVideoList();
      this.video.title = '';
      this.video.sort = 0;
      this.video.videoSourceId = '';
      this.saveVideoBtnDisabled = false;
    },
    // 编辑课时
    editVideo(videoId) {
      this.dialogVideoFormVisible = true;
      video.getById(videoId).then(response => {
        this.video = response.data.item;
        this.fileList =
          [{ 'name': this.video.videoOriginalName }];
      });
    },
    // 删除课时
    removeVideo(videoId) {
      this.$confirm('此操作将删除该课时, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return video.removeById(videoId);
      }).then(() => {
        this.fetchChapterVideoList();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(response => {
        if (response === 'cancel') {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        } else {
          this.$message({
            type: 'error',
            message: response.message
          });
        }
      });
    },
    // 成功回调
    handleVodUploadSuccess(response, file, fileList) {
      this.video.videoSourceId = response.data.videoId;
      this.video.videoOriginalName = file.name;
    },
    // 失败回调
    handleVodUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频');
    },
    beforeVodRemove() {
      return this.$confirm('确定要删除视频吗？');
    },
    handleVodRemove() {
      vod.removeById(this.video.videoSourceId).then(() => {
        this.video.videoSourceId = '';
        this.video.videoOriginalName = '';
        this.fileList = []
        this.$message({
          type: 'success',
          message: '删除视频成功!'
        });
      });
    }
  }
};
</script>
<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}

.chanpterList li {
  position: relative;
}

.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}

.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}

.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}
</style>

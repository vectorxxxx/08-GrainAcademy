<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布课程信息</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/oss/upload/file?host=cover'"
          class="avatar-uploader">
          <img v-if="courseInfo.cover" :src="courseInfo.cover" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
import teacher from '@/api/edu/teacher'
import Tinymce from '@/components/Tinymce'

const defaultForm = {
  title: '',
  subjectId: '',
  teacherId: '',
  lessonNum: 0,
  description: '',
  cover: process.env.OSS_PATH + '/cover/default.gif',
  price: 0
}

export default {
  // 声明组件
  components: { Tinymce },

  // 数据
  data() {
      return {
          courseInfo: defaultForm, // 表单数据
          subjectNestedList: [], // 所有的一级分类
          subSubjectList: [], // 所有二级分类
          teacherList: [], // 所有讲师
          saveBtnDisabled: false, // 保存按钮是否禁用
          BASE_API: process.env.BASE_API // 接口API地址
      }
  },

  // 监听器
  watch: {
      $route(to, from) {
          console.log('watch $route');
          this.init()
      }
  },

  // 创建周期
  created() {
      console.log('info created');
      this.init()
  },

  // 声明方法
  methods: {
      // 初始化
      init() {
          // 初始化课程基本信息
          if (this.$route.params && this.$route.params.id) {
              const id = this.$route.params.id
              this.fetchCourseInfoById(id)
          } else {
            this.courseInfo = { ...defaultForm }
            // 初始化课程分类列表
            this.initSubjectList()
            // 初始化讲师列表
            this.initTeacherList()
          }
      },

      // 查询课程信息
      fetchCourseInfoById(id) {
          course.getCourseInfoById(id).then(response => {
            this.courseInfo = response.data.item
            // 初始化课程分类列表
            subject.getNestedTreeList().then(response => {
              this.subjectNestedList = response.data.items
              for (let i = 0; i < this.subjectNestedList.length; i++) {
                const subjectNestedVO = this.subjectNestedList[i]
                if (subjectNestedVO.id === this.courseInfo.subjectParentId) {
                  this.subSubjectList = subjectNestedVO.children
                }
              }
            })
            // 初始化讲师列表
            this.initTeacherList()
          }).catch(response => {
            this.$message({
              type: 'error',
              message: response.message
            })
          })
      },

      // 初始化课程分类列表
      initSubjectList() {
          subject.getNestedTreeList().then(response => {
              this.subjectNestedList = response.data.items
          })
      },

      // 初始化讲师列表
      initTeacherList() {
          teacher.getList().then(response => {
              console.log(response.data.items)
              this.teacherList = response.data.items
          })
      },

      // 一级分类联动二级分类
      subjectLevelOneChanged(value) {
          console.log(value);
          for (let i = 0; i < this.subjectNestedList.length; i++) {
              const oneSubject = this.subjectNestedList[i]
              if (oneSubject.id === value) {
                  this.subSubjectList = oneSubject.children
                  this.courseInfo.subjectId = ''
              }
          }
      },

      // 下一步
      next() {
          console.log('next');
          this.saveBtnDisabled = true
          if (!this.courseInfo.id) {
              this.saveData()
          } else {
              this.updateData()
          }
      },

      // 保存数据
      saveData() {
          course.saveCourseInfo(this.courseInfo).then(response => {
              this.$message({
                  type: 'success',
                  message: '保存成功!'
              })
              return response
        }).then(response => {
          this.$router.push({ path: '/edu/course/chapter/' + response.data.courseId })
        }).catch(response => {
              this.$message({
                  type: 'error',
                  message: response.message
              })
          })
      },

      // 修改数据
      updateData() {
        course.updateCourseInfo(this.courseInfo).then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          return response
        }).then(response => {
          this.$router.push({ path: '/edu/course/chapter/' + response.data.courseId })
        }).catch(response => {
          this.$message({
            type: 'error',
            message: response.message
          })
        })
      },

      // 上传头像成功回调
      handleAvatarSuccess(res, file) {
        // 上传响应
        console.log(res)
        // base64编码
        console.log(URL.createObjectURL(file.raw))
        this.courseInfo.cover = res.data.url
      },

      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      }
  }
}
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>

<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input v-model="teacher.sort" controls-position="right" min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
        <!-- 弹出图片框 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+ '/oss/upload/avatar'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import teacher from '@/api/edu/teacher'
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

  const defaultForm = {
    name: '',
    sort: 0,
    level: 1,
    career: '',
    intro: '',
    avatar: process.env.OSS_PATH + '/avatar/default.jpg'
  }

  export default {
    components: { PanThumb, ImageCropper },
    data() {
      return {
        teacher: defaultForm,
        saveBtnDisabled: false, // 保存按钮是否禁用,
        BASE_API: process.env.BASE_API,
        imagecropperShow: false, // 是否显示上传组件
        imagecropperKey: 0 // 上传组件id
      }
    },

    // vue-router导航切换 时，如果两个路由都渲染同个组件，组件会重（chong）用,
    // 组件的生命周期钩子（created）不会再被调用, 使得组件的一些数据无法根据 path的改变得到更新
    // 因此：
    // 1、我们可以在watch中监听路由的变化，当路由变化时，重新调用created中的内容
    // 2、在init方法中我们判断路由的变化，如果是修改路由，则从api获取表单数据，
    //       如果是新增路由，则重新初始化表单数据
    watch: {
      $route(to, from) {
        console.log('watch $route');
        this.init()
      }
    },

    created() {
      console.log('created');
      this.init()
    },

    methods: {
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.fetchDataById(id)
        } else {
          this.teacher = { ...defaultForm }
        }
      },
      saveOrUpdate() {
        this.saveBtnDisabled = true
        if (this.teacher.id) {
          this.updateData()
        } else {
          this.saveData()
        }
      },
      saveData() {
        teacher.save(this.teacher).then(response => {
          return this.$message({
            type: 'success',
            message: '保存成功!'
          })
        }).then(response => {
          this.$router.push({
            path: '/edu/teacher/list'
          })
        }).catch(reponse => {
          this.$message({
            type: 'error',
            message: '保存失败'
          })
        })
      },
      fetchDataById(id) {
        teacher.getById(id).then(response => {
          this.teacher = response.data.item
        }).catch(reponse => {
          this.$message({
            type: 'error',
            message: '获取数据失败'
          })
        })
      },
      updateData() {
        teacher.updateById(this.teacher).then(response => {
          return this.$message({
            type: 'success',
            message: '修改成功'
          })
        }).then(response => {
          this.$router.push({
            path: '/edu/teacher/list'
          })
        }).catch(response => {
          return this.$message({
            type: 'error',
            message: '修改失败'
          })
        })
      },
      // 上传成功回调
      cropSuccess(data) {
        console.log(data)
        this.imagecropperShow = true
        this.teacher.avatar = data.url
        // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
        this.imagecropperKey = this.imagecropperKey + 1
      },
      // 关闭上传组件
      close() {
        this.imagecropperShow = false
        // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
        this.imagecropperKey = this.imagecropperKey + 1
      }
    }
  }
</script>

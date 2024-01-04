<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <!-- 所属分类 -->
      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select
          v-model="searchObj.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id" />
        </el-select>

        <!-- 二级分类 -->
        <el-select
          v-model="searchObj.subjectId"
          placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id" />
        </el-select>
      </el-form-item>

      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="searchObj.title" placeholder="课程标题" />
      </el-form-item>

      <!-- 讲师 -->
      <el-form-item>
        <el-select
          v-model="searchObj.teacherId"
          placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id" />
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList">

      <!-- 序号 -->
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <!-- 课程信息 -->
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>
        </template>
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>

      <!-- 发布时间 -->
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>

      <!-- 价格 -->
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{ Number(scope.row.price) === 0 ? '免费' :
          '¥' + scope.row.price.toFixed(2) }}
        </template>
      </el-table-column>

      <!-- 付费学员 -->
      <el-table-column label="付费学员" prop="buyCount" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.buyCount }}人
        </template>
      </el-table-column>

      <!-- 操作 -->
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
          </router-link>
          <router-link :to="'/edu/course/chapter/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="fetchData" />
  </div>
</template>
<script>
import course from '@/api/edu/course';
import teacher from '@/api/edu/teacher';
import subject from '@/api/edu/subject';

export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // 数据列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 10, // 每页记录数
      searchObj: {
        title: '',
        teacherId: '',
        subjectId: '',
        subjectParentId: ''
      }, // 查询条件
      teacherList: [], // 讲师列表
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [] // 二级分类列表
    };
  },

  created() {
    // 加载数据
    this.fetchData();
    // 初始化分类列表
    this.initSubjectList();
    // 初始化讲师列表
    this.initTeacherList();
  },

  methods: {
    // 加载数据
    fetchData(page = 1) {
      this.listLoading = true;
      this.page = page;
      course.getPageList(this.page, this.limit, this.searchObj).then(response => {
        if (response.success) {
          this.total = response.data.total;
          this.list = response.data.rows;
          this.listLoading = false;
        }
      });
    },
    // 初始化分类列表
    initSubjectList() {
      subject.getNestedTreeList().then(response => {
        this.subjectNestedList = response.data.items;
      });
    },
    // 初始化讲师列表
    initTeacherList() {
      teacher.getList().then(response => {
        this.teacherList = response.data.items;
      });
    },
    // 一级分类、二级分类联动
    subjectLevelOneChanged(value) {
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        const subjectNestedListElement = this.subjectNestedList[i];
        if (subjectNestedListElement.id === value) {
          this.subSubjectList = subjectNestedListElement.children;
          this.searchObj.subjectId = '';
        }
      }
    },
    // 重置数据
    resetData() {
      this.searchObj = {};
      this.subjectNestedList = []; // 二级分类列表
      this.fetchData();
    },
    // 删除课程
    removeDataById(courseId) {
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return course.removeById(courseId);
      }).then(() => {
        this.fetchData();
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
    }
  }
};
</script>
<style scoped>
.myClassList .info {
  width: 450px;
  overflow: hidden;
}

.myClassList .info .pic {
  width: 150px;
  height: 90px;
  overflow: hidden;
  float: left;
}

.myClassList .info .pic a {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

.myClassList .info .pic img {
  display: block;
  width: 100%;
}

.myClassList td .info .title {
  width: 280px;
  float: right;
  height: 90px;
}

.myClassList td .info .title a {
  display: block;
  height: 48px;
  line-height: 24px;
  overflow: hidden;
  color: #00baf2;
  margin-bottom: 12px;
}

.myClassList td .info .title p {
  line-height: 20px;
  margin-top: 5px;
  color: #818181;
}
</style>

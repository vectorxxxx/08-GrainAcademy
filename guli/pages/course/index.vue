<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="javascript:void(0);" @click="searchOne('')">全部</a>
                </li>
                <li v-for="(item, index) in subjectNestedList" :key="index" :class="{active: oneIndex === index}">
                  <a :title="item.title" href="javascript:void(0);" @click="searchOne(item.id, index)">{{
                    item.title
                  }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"/>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(item, index) in subSubjectList" :key="index" :class="{active: twoIndex === index}">
                  <a :title="item.title" href="javascript:void(0);" @click="searchTwo(item.id, index)">{{
                    item.title
                  }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"/>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange': buyCountSort !== ''}">
                <a title="销量" href="javascript:void(0);" @click="searchByCount()">销量
                  <span :class="{hide: buyCountSort === ''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange': gmtCreateSort !== ''}">
                <a title="最新" href="javascript:void(0);" @click="searchByGmtCreate()">最新
                  <span :class="{hide: gmtCreateSort === ''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange': priceSort !== ''}">
                <a title="价格" href="javascript:void(0);" @click="searchByPrice()">价格&nbsp;
                  <span :class="{hide: priceSort === ''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section v-if="data.total === 0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article v-if="data.total > 0" class="comm-course-list">
            <ul id="bna" class="of">
              <li v-for="course in data.rows" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" :alt="course.title" class="img-responsive">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a
                      :href="'/course/'+course.id"
                      title="course.title"
                      class="course-title fsize18 c-333">{{ course.title }}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span v-if="Number(course.price) === 0" class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span v-if="Number(course.price) !== 0" class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA">￥{{ course.price }}</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ course.viewCount }}人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"/>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <a
              :class="{undisable: !data.hasPrevious}"
              title="首页"
              href="javascript:void(0)"
              @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: !data.hasPrevious}"
              href="javascript:void(0)"
              title="上一页"
              @click.prevent="gotoPage(data.current - 1)">&lt;</a>
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current === page,undisable: data.current === page}"
              :title="'第'+page+'页'"
              href="javascript:void(0)"
              @click.prevent="gotoPage(page)">{{ page }}</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="javascript:void(0)"
              title="下一页"
              @click.prevent="gotoPage(data.current + 1)">&gt;</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="javascript:void(0)"
              title="末页"
              @click.prevent="gotoPage(data.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import course from '../../api/course'

const limit = 4
export default {
  data() {
    return {
      page: 1,
      data: {},
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表
      searchObj: {}, // 查询表单对象
      oneIndex: -1,
      twoIndex: -1,
      buyCountSort: '',
      gmtCreateSort: '',
      priceSort: ''
    }
  },

  created() {
    // 获取课程列表
    this.initCourse()

    // 获取分类
    this.initSubject()
  },

  methods: {
    // 获取课程列表
    initCourse() {
      course.getPageList(1, limit, this.searchObj).then(response => {
        this.data = response.data.data
      })
    },
    // 获取一级分类
    initSubject() {
      course.getNestedTreeList().then(response => {
        this.subjectNestedList = response.data.data.items
      })
    },
    // 点击一级分类，显示对应的二级分类，查询数据
    searchOne(subjectParentId, index) {
      this.oneIndex = index
      this.twoIndex = -1

      this.searchObj.subjectId = ''
      this.subSubjectList = []

      this.searchObj.subjectParentId = subjectParentId
      this.gotoPage(this.page)

      for (let i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === subjectParentId) {
          this.subSubjectList = this.subjectNestedList[i].children
        }
      }
    },
    // 点击二级分类，查询数据
    searchTwo(subjectId, index) {
      this.twoIndex = index
      this.searchObj.subjectId = subjectId
      this.gotoPage(this.page)
    },
    // 购买量查询
    searchByCount() {
      this.buyCountSort = '1'
      this.gmtCreateSort = ''
      this.priceSort = ''
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      this.gotoPage(this.page)
    },
    // 最新时间查询
    searchByGmtCreate() {
      this.buyCountSort = ''
      this.gmtCreateSort = '1'
      this.priceSort = ''
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      this.gotoPage(this.page)
    },
    // 价格查询
    searchByPrice() {
      this.buyCountSort = ''
      this.gmtCreateSort = ''
      this.priceSort = '1'
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort
      this.searchObj.priceSort = this.priceSort
      this.gotoPage(this.page)
    },
    // 分页查询
    gotoPage(page) {
      this.page = page
      course.getPageList(page, limit, this.searchObj).then(response => {
        this.data = response.data.data
      })
    }
  }
}
</script>

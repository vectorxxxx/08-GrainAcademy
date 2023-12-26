<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="过滤关键字" style="margin-bottom:30px;" />

    <el-tree
      ref="subjectTree"
      :data="sujectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all/>
  </div>
</template>
<script>
import subject from '@/api/edu/subject'
export default {
    data() {
        return {
            filterText: '',
            sujectList: [],
            defaultProps: {
                children: 'children',
                label: 'title'
            }
        }
    },

    watch: {
        filterText(val) {
            this.$refs.subjectTree.filter(val)
        }
    },

    created() {
        this.fetchNodeList()
    },

    methods: {
        fetchNodeList() {
            subject.getNestedTreeList().then(response => {
                if (response.success) {
                    this.sujectList = response.data.items
                }
            })
        },
        filterNode(value, data) {
            if (!value) return true
            return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
        }
    }

}
</script>

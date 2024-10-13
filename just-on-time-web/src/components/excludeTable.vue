<template>
  <el-table :data="excludeTimeList" style="width: 100%">
    <el-table-column label='序号' type="index" :index="indexMethod" />
    <el-table-column label="开始时间" prop="start" />
    <el-table-column label="结束时间" prop="end" />
    <el-table-column align="right">
      <template #header>
        <!-- Form -->
        <el-button type="primary" @click="dialogFormVisible = true">添加排除规则</el-button>
      </template>
      <template #default="scope">
        <!-- <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
          Edit
        </el-button> -->

        <el-popconfirm confirm-button-text="Yes" cancel-button-text="No" icon-color="#626AEF"
          title="Are you sure to delete this?" @confirm="deleteExcludeTimeRange(scope.$index, scope.row)">
          <template #reference>
            <el-button type="danger">Delete</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog v-model="dialogFormVisible" title="Shipping address">


    <div class="demo-time-range">
      <el-time-select v-model="excludeTime.startTime" :max-time="excludeTime.endTime" class="mr-4"
        placeholder="Start time" start="08:30" step="00:15" end="18:30" />
      <el-time-select v-model="excludeTime.endTime" :min-time="excludeTime.startTime" placeholder="End time"
        start="08:30" step="00:15" end="18:30" />
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addExcludeTimeRange">确认添加</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import { computed, ref } from 'vue'


interface User {
  date: string
  name: string
  address: string
}

export default {
  data() {
    return {
      search: "",
      dialogFormVisible: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '140px',
      excludeTime: {
        startTime: null,
        endTime: null
      },
      excludeTimeList: [ ]
    }
  },
  created() {
    this.fetchExcludeTime()
  },
  methods: {
    handleEdit(index: number, row: User) {
      console.log(index, row)
    },
    deleteExcludeTimeRange(index: number, row: User) {
      console.log(index, row)
    },
    indexMethod(index: number) {
      return index
    },
    addExcludeTimeRange() {
      this.dialogFormVisible = false
    },
    fetchExcludeTime() {
            this.$http.get('http://localhost:54322/api/excludeTimeRange')
            .then(response => {
                this.excludeTimeList = response.data['data']
            })
            .catch(error => {
                console.log(error)
            })
        }
  }
}

</script>

<style></style>

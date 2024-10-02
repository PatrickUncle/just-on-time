<script setup>
</script>

<template>
  <h1>just-on-time</h1>

  <el-tabs type="border-card">
    <el-tab-pane label="工时预览">
      <el-descriptions title="用户信息">
        <el-descriptions-item label="总工时">180</el-descriptions-item>
        <el-descriptions-item label="超出工时">10.00</el-descriptions-item>
        <el-descriptions-item label="日均工时">9.00</el-descriptions-item>
        <el-descriptions-item label="所欠工时">0.00</el-descriptions-item>
        <el-descriptions-item label="统计天数">3</el-descriptions-item>
        <el-descriptions-item label="统计刷新时间"
          >2024-09-10</el-descriptions-item
        >
        <el-descriptions-item label="本月剩余工作日">20</el-descriptions-item>
        <el-descriptions-item label="今日建议下班时间"
          >18.00</el-descriptions-item
        >
      </el-descriptions>

      <el-card class="box-card">
        <el-calendar v-model="value"> </el-calendar>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="考勤设置">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>基本考勤参数设置</span>
        </div>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="审批人">
            <el-input v-model="formInline.user" placeholder="审批人"></el-input>
          </el-form-item>
          <el-form-item label="活动区域">
            <el-select v-model="formInline.region" placeholder="活动区域">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>工时排除范围管理</span>
        </div>
        <el-table
          :data="
            tableData.filter(
              (data) =>
                !search ||
                data.name.toLowerCase().includes(search.toLowerCase())
            )
          "
          style="width: 100%"
        >
          <el-table-column label="Date" prop="date"> </el-table-column>
          <el-table-column label="Name" prop="name"> </el-table-column>
          <el-table-column align="right">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"
              />
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >Edit</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >Delete</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="系统设置">
      
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  data() {
    return {
      value: new Date(),
      formInline: {
        user: "",
        region: "",
      },
      tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }],
        search: ''
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }
  },
};
</script>

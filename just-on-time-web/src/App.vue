<script setup>
import excludetable from './components/excludeTable.vue'
import myDate from './components/myDate.vue'
import overview from './components/overview.vue'

</script>

<template>
  <h1>just-on-time</h1>

  <el-tabs type="border-card">
    <el-tab-pane label="工时预览">
      <overview></overview>
      <el-card class="box-card">
        <myDate></myDate>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="考勤设置">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>基本考勤参数设置</span>
        </div>
        <el-form :inline="true" :model="baseParam" class="demo-form-inline">
          <el-form-item label="最早工时统计时间" >
            <el-time-select v-model="baseParam.startTime" :max-time="baseParam.endTime" class="mr-4" placeholder="Start time" start="00:00"
              step="00:15" end="24:00" style="width: 200px" />
          </el-form-item>
          <el-form-item label="最晚工时统计时间">
            <el-time-select v-model="baseParam.endTime" :min-time="baseParam.startTime" placeholder="End time"  start="00:00"
            step="00:15" end="24:00" style="width: 200px"/>
          </el-form-item>

          <el-form-item label="目标日均工时">
            <el-input v-model="baseParam.target" placeholder="目标日均工时" style="width: 120px"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">保存参数并重新计算</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>工时排除范围管理</span>
        </div>
        <excludetable></excludetable>
      </el-card>

    </el-tab-pane>
    <el-tab-pane label="系统设置"> </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  data() {
    return {
      baseParam: {
        startTime: null,
        endTime: null,
        target: 8
      }
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    }
  },
};
</script>

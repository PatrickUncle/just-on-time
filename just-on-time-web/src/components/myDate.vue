<template>
  <el-calendar v-model="value" >
    <template #date-cell="{ data }">
      <div :class="[isGreatDay(data) ? 'great-day' : 'bad-day', isCurrentMonth(data) ? '' : 'hide']">
        {{ data.day}}
        
        
        <p>工时为:{{ dateMap[data.day] == null ? 0 : dateMap[data.day]['total']}}</p>
        
      </div>
    </template>
  </el-calendar>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
const value = ref(new Date())


const dateMap =
    {
        "2024-10-10": {
            "time":"2024-10-10",
            "total":99
        },
        "2024-10-11": {
            "time":"2024-10-11",
            "total":20
        },
        "2024-10-12": {
            "time":"2024-10-12",
            "total":30
        },
        "2024-10-13": {
            "time":"2024-10-13",
            "total":66
        },
        "2024-10-14": {
            "time":"2024-10-14",
            "total":10
        }
    }


function isGreatDay(data) {
    console.log(data.day)
    console.log(dateMap)
    const item = dateMap[data.day]
    console.log(item)
    return item != null && item['total'] > 50
}

function isCurrentMonth(data) {
    // 获取当前显示的年月
      return true
}


</script>

<style>
.is-selected {
  color: #1989fa;
}

.great-day {
  background-color: green;
}
.bad-day {
  background-color: red;
}
.hide {
  display: none;
}
</style>
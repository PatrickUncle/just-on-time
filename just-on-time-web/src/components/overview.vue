<template>
    <el-descriptions title="用户信息">
        <el-descriptions-item label="总工时">{{ basicData.total }}</el-descriptions-item>
        <el-descriptions-item label="超出工时">{{ basicData.over }}</el-descriptions-item>
        <el-descriptions-item label="日均工时">{{ basicData.avg }}</el-descriptions-item>
        <el-descriptions-item label="所欠工时">{{ basicData.lack }}</el-descriptions-item>
        <el-descriptions-item label="统计天数">{{ basicData.dayCount }}</el-descriptions-item>
        <el-descriptions-item label="统计刷新时间">{{ basicData.refreshTime }}</el-descriptions-item>
        <el-descriptions-item label="本月剩余工作日">{{ basicData.remainDay }}</el-descriptions-item>
        <el-descriptions-item label="今日建议下班时间">{{ basicData.adviceBackHomeTime }}</el-descriptions-item>
    </el-descriptions>
</template>

<script lang="ts">
import { computed, ref } from 'vue'

export default {
    data() {
        return {
            basicData: {
                "total": 180,
                "over": 10.00,
                "avg": 9.00,
                "lack": 0.00,
                "refreshTime": "2024-10-13",
                "dayCount": 3,
                "remainDay": 20,
                "adviceBackHomeTime": "18:00"
            }
        }
    },

    created() {
        this.fetchBasicData()
    },
    methods: {
        fetchBasicData() {
            this.$http.get('http://localhost:54322/api/base')
            .then(response => {
                this.basicData = response.data['data']
            })
            .catch(error => {
                console.log(error)
            })
        }

    }
}
</script>

<style></style>
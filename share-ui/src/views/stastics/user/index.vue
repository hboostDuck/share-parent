<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="68px">
      <el-form-item label="查询年份" prop="orderNo">
        <el-input
            v-model="searchObj.selectYear"
            placeholder="请输入年份"
            clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="showChart">查询</el-button>
      </el-form-item>
    </el-form>
    <div ref="chart" style="width: 600px; height: 400px;"></div>
  </div>
</template>
<script>
import { getUserCount } from "@/api/sta/sta";
import * as echarts from 'echarts';
export default {
  data() {
    return {
      searchObj: {
        selectYear: ''
      },
      btnDisabled: false,
      chart: null,
      title: '',
      xData: [], // x轴数据
      yData: [] // y轴数据
    }
  },
  created() {
    this.showChart()
  },
  methods: {
    // 初始化图表数据
    showChart() {
      getUserCount().then(response => {
        this.yData = response.data.countList
        this.xData = response.data.dateList
        this.setChartData()
      })
    },
    setChartData() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(this.$refs.chart)
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: this.title + '注册量统计'
        },
        tooltip: {},
        legend: {
          data: [this.title]
        },
        xAxis: {
          data: this.xData
        },
        yAxis: {
          minInterval: 1
        },
        series: [{
          name: this.title,
          type: 'bar',
          data: this.yData
        }]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    },
  }
}
</script>

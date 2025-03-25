<template>
  <div>
    <div style="margin-top: 10px">
      <label for="month">选择年月:</label>
      <input type="month" id="month" v-model="selectedMonth" @change="fetchEmployeeEcMonthInfo" />
    </div>
    <div  style="margin-top: 20px">
      <h2>月度奖惩分值统计 ({{ selectedMonth }})</h2>
      <el-table :data="employeeEcMonthInfo" border stripe>
        <el-table-column width="120" prop="eid" label="员工ID"></el-table-column>
        <el-table-column width="120" prop="name" label="姓名"></el-table-column>
        <el-table-column width="120" prop="ecpoint" label="奖惩分值"></el-table-column>
        <el-table-column width="120" label="奖惩类别">
          <template slot-scope="scope">
            <el-tag :type="scope.row.ecpoint >= 50 ? 'success' : 'danger'">
              {{ scope.row.ecpoint >= 50 ? '工作态度正常' : '工作态度消极' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "PerEc",
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '添加奖惩记录',
      employeeEcs: [],
      employees: [],
      employeeEc: {
        id: null,
        eid: null,
        ecdate: '',
        ecreason: '',
        ecpoint: 0,
        ectype: '0', // 初始化为字符串 '0'
        remark: ''
      },
      selectedMonth: '',
      employeeEcMonthInfo: []
    }
  },
  mounted() {
    this.initEmployeeEcs();
    this.initEmployees();
  },
  methods: {
    initEmployeeEcs() {
      this.getRequest("/employee/ec/").then(resp => {
        if (resp) {
          this.employeeEcs = resp;
        }
      })
    },
    initEmployees() {
      this.getRequest("/employee/basic/").then(resp => {
        if (resp) {
          this.employees = resp.data;
        }
      })
    },
    dateFormat(row, column, cellValue) {
      if (cellValue) {
        return new Date(cellValue).toLocaleDateString('en-CA');
      }
      return '';
    },
    fetchEmployeeEcMonthInfo() {
      if (this.selectedMonth) {
        this.getRequest(`/employee/ec/getEmployeeecMonthInfo?month=${this.selectedMonth}`).then(resp => {
          if (resp) {
            this.employeeEcMonthInfo = resp;
          }
        }).catch(error => {
          this.$message.error("获取数据失败: " + error.message);
        });
      }
    }
  }
}
</script>

<style scoped>
.loading {
  color: #409EFF;
}
.error {
  color: #F56C6C;
}
</style>

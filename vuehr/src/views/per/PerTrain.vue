<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <el-button icon="el-icon-plus" type="primary" @click="showAddEmployeeTrainView">添加培训记录</el-button>
      <el-button icon="el-icon-refresh" type="success" @click="initEmployeeTrains">刷新</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table :data="employeeTrains" border stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column width="120" prop="name" label="员工姓名"></el-table-column>
        <el-table-column width="100" prop="traindate" label="培训日期" :formatter="dateFormat"></el-table-column>
        <el-table-column width="150" prop="traincontent" label="培训内容"></el-table-column>
        <el-table-column width="150" prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="showEditEmployeeTrainView(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteEmployeeTrain(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form :model="employeeTrain" label-width="100px">
        <el-form-item label="员工">
          <el-select v-model="employeeTrain.eid" placeholder="请选择员工" filterable>
            <el-option
                v-for="employee in employees"
                :key="employee.id"
                :label="employee.name"
                :value="employee.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="培训日期">
          <el-date-picker
              v-model="employeeTrain.traindate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="培训内容">
          <el-input v-model="employeeTrain.traincontent"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="employeeTrain.remark"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEmployeeTrain">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PerTrain",
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '添加培训记录',
      employeeTrains: [],
      employees: [],
      employeeTrain: {
        id: null,
        eid: null,
        traindate: '',
        traincontent: '',
        remark: ''
      }
    }
  },
  mounted() {
    this.initEmployeeTrains();
    this.initEmployees();
  },
  methods: {
    showEditEmployeeTrainView(data) {
      this.dialogTitle = '修改培训记录';
      this.dialogVisible = true;
      this.employeeTrain = {...data};
    },
    deleteEmployeeTrain(data) {
      this.$confirm('此操作将删除【' + data.employee.name + '】的培训记录，是否继续？', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定'
      }).then(() => {
        this.deleteRequest("/employee/train/" + data.id).then(resp => {
          if (resp) {
            this.initEmployeeTrains();
          }
        })
      }).catch(() => {
        this.$message.info("取消删除!");
      })
    },
    showAddEmployeeTrainView() {
      this.dialogTitle = '添加培训记录';
      this.dialogVisible = true;
      this.employeeTrain = {
        id: null,
        eid: null,
        traindate: '',
        traincontent: '',
        remark: ''
      };
    },
    initEmployeeTrains() {
      this.getRequest("/employee/train/").then(resp => {
        if (resp) {
          this.employeeTrains = resp;
        }
      })
    },
    initEmployees() {
      this.getRequest("/employee/basic/?size=1000").then(resp => {
        if (resp) {
          this.employees = resp.data;
        }
      })
    },
    saveEmployeeTrain() {
      if (this.employeeTrain.id) {
        this.putRequest("/employee/train/", this.employeeTrain).then(resp => {
          if (resp) {
            this.initEmployeeTrains();
            this.dialogVisible = false;
          }
        })
      } else {
        this.postRequest("/employee/train/", this.employeeTrain).then(resp => {
          if (resp) {
            this.initEmployeeTrains();
            this.dialogVisible = false;
          }
        });
      }
    },
    dateFormat(row, column, cellValue) {
      if (cellValue) {
        return new Date(cellValue).toLocaleDateString('en-CA');
      }
      return '';
    }
  }
}
</script>

<style scoped>

</style>

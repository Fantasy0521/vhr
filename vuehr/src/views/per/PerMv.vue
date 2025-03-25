<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <el-button icon="el-icon-plus" type="primary" @click="showAddEmployeeRemoveView">添加离职记录</el-button>
      <el-button icon="el-icon-refresh" type="success" @click="initEmployeeRemoves">刷新</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table :data="employeeRemoves" border stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column width="120" prop="name" label="员工姓名"></el-table-column>
        <el-table-column width="100" prop="removedate" label="离职日期" :formatter="dateFormat"></el-table-column>
        <el-table-column width="150" prop="reason" label="离职原因"></el-table-column>
        <el-table-column width="150" prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="showEditEmployeeRemoveView(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteEmployeeRemove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form :model="employeeRemove" label-width="100px">
        <el-form-item label="员工">
          <el-select v-model="employeeRemove.eid" placeholder="请选择员工" filterable >
            <el-option
                v-for="employee in employees"
                :key="employee.id"
                :label="employee.name"
                :value="employee.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="离职日期">
          <el-date-picker
              v-model="employeeRemove.removedate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离职原因">
          <el-input v-model="employeeRemove.reason"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="employeeRemove.remark"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEmployeeRemove">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PerMv",
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '添加离职记录',
      employeeRemoves: [],
      employees: [],
      employeeRemove: {
        id: null,
        eid: null,
        afterdepid: null,
        afterjobid: null,
        removedate: '',
        reason: '',
        remark: ''
      }
    }
  },
  mounted() {
    this.initEmployeeRemoves();
    this.initEmployees();
  },
  methods: {
    showEditEmployeeRemoveView(data) {
      this.dialogTitle = '修改离职记录';
      this.dialogVisible = true;
      this.employeeRemove = {...data};
    },
    deleteEmployeeRemove(data) {
      this.$confirm('此操作将删除【' + data.employee.name + '】的离职记录，是否继续？', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定'
      }).then(() => {
        this.deleteRequest("/employee/remove/" + data.id).then(resp => {
          if (resp) {
            this.initEmployeeRemoves();
          }
        })
      }).catch(() => {
        this.$message.info("取消删除!");
      })
    },
    showAddEmployeeRemoveView() {
      this.dialogTitle = '添加离职记录';
      this.dialogVisible = true;
      this.employeeRemove = {
        id: null,
        eid: null,
        afterdepid: null,
        afterjobid: null,
        removedate: '',
        reason: '',
        remark: ''
      };
    },
    initEmployeeRemoves() {
      this.getRequest("/employee/remove/").then(resp => {
        if (resp) {
          this.employeeRemoves = resp;
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
    saveEmployeeRemove() {
      if (this.employeeRemove.id) {
        this.putRequest("/employee/remove/", this.employeeRemove).then(resp => {
          if (resp) {
            this.initEmployeeRemoves();
            this.dialogVisible = false;
          }
        })
      } else {
        this.postRequest("/employee/remove/", this.employeeRemove).then(resp => {
          if (resp) {
            this.initEmployeeRemoves();
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
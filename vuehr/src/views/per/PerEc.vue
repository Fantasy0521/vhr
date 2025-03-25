<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <el-button icon="el-icon-plus" type="primary" @click="showAddEmployeeEcView">添加奖惩记录</el-button>
      <el-button icon="el-icon-refresh" type="success" @click="initEmployeeEcs">刷新</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table :data="employeeEcs" border stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column width="120" prop="name" label="员工姓名"></el-table-column>
        <el-table-column width="100" prop="ecdate" label="奖惩日期" :formatter="dateFormat"></el-table-column>
        <el-table-column width="150" prop="ecreason" label="奖惩原因"></el-table-column>
        <el-table-column width="80" prop="ecpoint" label="奖惩分数"></el-table-column>
        <el-table-column width="80" label="奖惩类别">
          <template slot-scope="scope">
            <el-tag :type="scope.row.ectype === 0 ? 'success' : 'danger'">
              {{ scope.row.ectype === 0 ? '奖' : '罚' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column width="150" prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="showEditEmployeeEcView(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteEmployeeEc(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form :model="employeeEc" label-width="100px">
        <el-form-item label="员工">
          <el-select v-model="employeeEc.eid" placeholder="请选择员工" filterable>
            <el-option
                v-for="employee in employees"
                :key="employee.id"
                :label="employee.name"
                :value="employee.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="奖惩日期">
          <el-date-picker
              v-model="employeeEc.ecdate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="奖惩原因">
          <el-input v-model="employeeEc.ecreason"></el-input>
        </el-form-item>
        <el-form-item label="奖惩分数">
          <el-input v-model="employeeEc.ecpoint"></el-input>
        </el-form-item>
        <el-form-item label="奖惩类别">
          <el-select v-model="employeeEc.ectype" placeholder="请选择">
            <el-option label="奖" value="0"></el-option>
            <el-option label="罚" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="employeeEc.remark"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEmployeeEc">保存</el-button>
      </span>
    </el-dialog>
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
      }
    }
  },
  mounted() {
    this.initEmployeeEcs();
    this.initEmployees();
  },
  methods: {
    showEditEmployeeEcView(data) {
      this.dialogTitle = '修改奖惩记录';
      this.dialogVisible = true;
      // 确保 ectype 是字符串类型
      this.employeeEc = { ...data, ectype: data.ectype.toString() };
    },
    deleteEmployeeEc(data) {
      this.$confirm('此操作将删除【' + data.employee.name + '】的奖惩记录，是否继续？', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定'
      }).then(() => {
        this.deleteRequest("/employee/ec/" + data.id).then(resp => {
          if (resp) {
            this.initEmployeeEcs();
          }
        })
      }).catch(() => {
        this.$message.info("取消删除!");
      })
    },
    showAddEmployeeEcView() {
      this.dialogTitle = '添加奖惩记录';
      this.dialogVisible = true;
      this.employeeEc = {
        id: null,
        eid: null,
        ecdate: '',
        ecreason: '',
        ecpoint: 0,
        ectype: '0', // 初始化为字符串 '0'
        remark: ''
      };
    },
    initEmployeeEcs() {
      this.getRequest("/employee/ec/").then(resp => {
        if (resp) {
          this.employeeEcs = resp;
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
    saveEmployeeEc() {
      if (this.employeeEc.id) {
        this.putRequest("/employee/ec/", this.employeeEc).then(resp => {
          if (resp) {
            this.initEmployeeEcs();
            this.dialogVisible = false;
          }
        })
      } else {
        this.postRequest("/employee/ec/", this.employeeEc).then(resp => {
          if (resp) {
            this.initEmployeeEcs();
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
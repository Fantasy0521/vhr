<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <el-button icon="el-icon-plus" type="primary" @click="showAddAdjustSalaryView">添加调薪记录</el-button>
      <el-button icon="el-icon-refresh" type="success" @click="initAdjustSalaries">刷新</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table :data="adjustSalaries" border stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column width="120" prop="name" label="员工姓名"></el-table-column>
        <el-table-column width="100" prop="asDate" label="调薪日期" :formatter="dateFormat"></el-table-column>
        <el-table-column width="100" prop="beforeSalary" label="调前薪资"></el-table-column>
        <el-table-column width="100" prop="afterSalary" label="调后薪资"></el-table-column>
        <el-table-column width="150" prop="reason" label="调薪原因"></el-table-column>
        <el-table-column width="150" prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="showEditAdjustSalaryView(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteAdjustSalary(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form :model="adjustSalary" label-width="100px">
        <el-form-item label="员工">
          <el-select v-model="adjustSalary.eid" placeholder="请选择员工" filterable @change="handleEmployeeChange">
            <el-option
                v-for="employee in employees"
                :key="employee.id"
                :label="employee.name"
                :value="employee.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调薪日期">
          <el-date-picker
              v-model="adjustSalary.asDate"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="调前薪资">
          <el-input v-model="adjustSalary.beforeSalary" readonly></el-input>
        </el-form-item>
        <el-form-item label="调后薪资">
          <el-input v-model="adjustSalary.afterSalary"></el-input>
        </el-form-item>
        <el-form-item label="调薪原因">
          <el-input v-model="adjustSalary.reason"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="adjustSalary.remark"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveAdjustSalary">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PerSalary",
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '添加调薪记录',
      adjustSalaries: [],
      employees: [],
      adjustSalary: {
        id: null,
        eid: null,
        asDate: '',
        beforeSalary: 0,
        afterSalary: 0,
        reason: '',
        remark: ''
      }
    }
  },
  mounted() {
    this.initAdjustSalaries();
    this.initEmployees();
  },
  methods: {
    showEditAdjustSalaryView(data) {
      this.dialogTitle = '修改调薪记录';
      this.dialogVisible = true;
      this.adjustSalary = {...data};
    },
    deleteAdjustSalary(data) {
      this.$confirm('此操作将删除【' + data.name + '】的调薪记录，是否继续？', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定'
      }).then(() => {
        this.deleteRequest("/salary/adjust/" + data.id).then(resp => {
          if (resp) {
            this.initAdjustSalaries();
          }
        })
      }).catch(() => {
        this.$message.info("取消删除!");
      })
    },
    showAddAdjustSalaryView() {
      this.dialogTitle = '添加调薪记录';
      this.dialogVisible = true;
      this.adjustSalary = {
        id: null,
        eid: null,
        asDate: '',
        beforeSalary: 0,
        afterSalary: 0,
        reason: '',
        remark: ''
      };
    },
    initAdjustSalaries() {
      this.getRequest("/salary/adjust/").then(resp => {
        if (resp) {
          this.adjustSalaries = resp;
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
    saveAdjustSalary() {
      if (this.adjustSalary.id) {
        this.putRequest("/salary/adjust/", this.adjustSalary).then(resp => {
          if (resp) {
            this.initAdjustSalaries();
            this.dialogVisible = false;
          }
        })
      } else {
        this.postRequest("/salary/adjust/", this.adjustSalary).then(resp => {
          if (resp) {
            this.initAdjustSalaries();
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
    },
    handleEmployeeChange(eid) {
      if (eid) {
        this.getRequest(`/salary/sob/employee?eid=`+eid).then(resp => {
          if (resp && resp.basicSalary) {
            this.adjustSalary.beforeSalary = resp.basicSalary;
          } else {
            this.adjustSalary.beforeSalary = 0;
            this.$message.warning("无法获取员工的当前薪资");
          }
        }).catch(() => {
          this.adjustSalary.beforeSalary = 0;
          this.$message.error("获取员工薪资失败");
        });
      } else {
        this.adjustSalary.beforeSalary = 0;
      }
    }
  }
}
</script>

<style scoped>

</style>

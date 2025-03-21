<template>
  <div>
<!--    <div style="display: flex; justify-content: space-between">-->
<!--      <div>-->
<!--        <el-input placeholder="请输入操作内容进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"-->
<!--                  clearable-->
<!--                  @clear="initOpLogs"-->
<!--                  style="width: 350px;margin-right: 10px" v-model="keyword"-->
<!--                  @keydown.enter.native="initOpLogs"></el-input>-->
<!--        <el-button icon="el-icon-search" type="primary" @click="initOpLogs">-->
<!--          搜索-->
<!--        </el-button>-->
<!--      </div>-->
<!--    </div>-->
    <div style="margin-top: 10px">
      <el-table
          :data="opLogs"
          stripe
          border
          v-loading="loading"
          element-loading-text="正在加载..."
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          style="width: 100%">
        <el-table-column
            prop="adddate"
            label="操作日期"
            :formatter="dateFormat"
            width="180">
        </el-table-column>
        <el-table-column
            prop="operate"
            label="操作内容"
            width="200">
        </el-table-column>
        <el-table-column
            prop="name"
            label="操作人"
            width="120">
        </el-table-column>
        <el-table-column
            prop="address"
            label="操作地址"
            width="150">
        </el-table-column>
        <el-table-column
            prop="username"
            label="操作账号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="userface"
            label="操作人头像"
            width="150">
          <template slot-scope="scope">
            <img :src="scope.row.userface" alt="头像" style="width: 50px; height: 50px; border-radius: 50%;">
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex; justify-content: flex-end">
        <el-pagination
            background
            @current-change="currentChange"
            @size-change="sizeChange"
            layout="sizes, prev, pager, next, jumper, ->, total, slot"
            :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysLog",
  data() {
    return {
      keyword: '',
      opLogs: [],
      loading: false,
      total: 0,
      page: 1,
      size: 10
    }
  },
  mounted() {
    this.initOpLogs();
  },
  methods: {
    initOpLogs() {
      this.loading = true;
      let url = '/system/basic/oplog/?page=' + this.page + '&size=' + this.size;
      if (this.keyword) {
        url += '&keyword=' + this.keyword;
      }
      this.getRequest(url).then(resp => {
        this.loading = false;
        if (resp) {
          this.opLogs = resp.data;
          this.total = resp.total;
        }
      });
    },
    sizeChange(currentSize) {
      this.size = currentSize;
      this.initOpLogs();
    },
    currentChange(currentPage) {
      this.page = currentPage;
      this.initOpLogs();
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
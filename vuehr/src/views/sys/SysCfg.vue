<template>
  <div>
    <div style="display: flex; justify-content: space-between">
      <el-button icon="el-icon-plus" type="primary" @click="showAddMenuView">添加菜单</el-button>
      <el-button icon="el-icon-refresh" type="success" @click="initMenus">刷新</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table :data="menus" border stripe row-key="id" default-expand-all :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column prop="name" label="菜单名称"></el-table-column>
        <el-table-column prop="path" label="路径"></el-table-column>
        <el-table-column prop="component" label="组件"></el-table-column>
        <!--        <el-table-column prop="iconCls" label="图标"></el-table-column> -->
        <el-table-column prop="url" label="URL"></el-table-column>
        <el-table-column prop="enabled" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.enabled ? 'success' : 'danger'">{{ scope.row.enabled ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="showEditMenuView(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteMenu(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form :model="menu" label-width="100px">
        <el-form-item label="菜单名称">
          <el-input v-model="menu.name"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="menu.path"></el-input>
        </el-form-item>
        <el-form-item label="组件">
          <el-input v-model="menu.component"></el-input>
        </el-form-item>
        <!--        <el-form-item label="图标"> -->
        <!--          <el-input v-model="menu.iconCls"></el-input> -->
        <!--        </el-form-item> -->
        <el-form-item label="URL">
          <el-input v-model="menu.url"></el-input>
        </el-form-item>
        <el-form-item label="父菜单">
          <el-select v-model="menu.parentId" placeholder="请选择父菜单">
            <el-option
                v-for="item in parentMenuOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="menu.enabled" active-text="启用" inactive-text="禁用"></el-switch>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveMenu">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "MenuManagement",
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '添加菜单',
      menus: [],
      parentMenuOptions: [],
      menu: {
        id: null,
        name: '',
        path: '',
        component: '',
        // iconCls: '',
        url: '',
        parentId: null,
        enabled: true
      }
    }
  },
  mounted() {
    this.initMenus();
    this.initParentMenuOptions();
  },
  methods: {
    showEditMenuView(data) {
      this.dialogTitle = '修改菜单';
      this.dialogVisible = true;
      this.menu = { ...data };
    },
    deleteMenu(data) {
      this.$confirm('此操作将删除【' + data.name + '】菜单，是否继续？', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定'
      }).then(() => {
        this.deleteRequest("/system/basic/menu/" + data.id).then(resp => {
          if (resp) {
            this.initMenus();
          }
        })
      }).catch(() => {
        this.$message.info("取消删除!");
      })
    },
    showAddMenuView() {
      this.dialogTitle = '添加菜单';
      this.dialogVisible = true;
      this.menu = {
        id: null,
        name: '',
        path: '',
        component: '',
        // iconCls: '',
        url: '',
        parentId: null,
        enabled: true
      };
    },
    initMenus() {
      this.getRequest("/system/basic/menu/").then(resp => {
        if (resp) {
          this.menus = resp;
        }
      })
    },
    initParentMenuOptions() {
      this.getRequest("/system/basic/menu/getByParentId?pid=1").then(resp => {
        if (resp) {
          this.parentMenuOptions = resp;
        }
      })
    },
    saveMenu() {
      if (this.menu.id) {
        this.putRequest("/system/basic/menu/", this.menu).then(resp => {
          if (resp) {
            this.initMenus();
            this.dialogVisible = false;
          }
        })
      } else {
        this.postRequest("/system/basic/menu/", this.menu).then(resp => {
          if (resp) {
            this.initMenus();
            this.dialogVisible = false;
          }
        });
      }
    }
  }
}
</script>

<style scoped>

</style>

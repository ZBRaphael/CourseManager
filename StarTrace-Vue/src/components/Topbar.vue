<template>
  <div class="header">
    <div class="logo">
      <img src="../assets/logo.png" alt />
      <h1>欢迎登录星迹</h1>
    </div>
    <el-menu :default-active="$route.path"
      class="el-menu-nav"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      router

    >
      <el-submenu index="/aboutme"  v-if="this.$root.levelid != 3">
        <template slot="title" >MENU</template>
<!--        <el-menu-item index="/aboutme">个人中心</el-menu-item>-->
        <el-menu-item @click="logout">退出登录</el-menu-item>
      </el-submenu>
      <el-menu-item index="/coach" v-if="this.$root.levelid == 1">
        <el-badge value="admin" class="item" type="warning">教练员</el-badge>
      </el-menu-item>
      <el-menu-item index="/students" v-if="this.$root.levelid == 0">
        <el-badge value="student" class="item" >学生</el-badge>
      </el-menu-item>
      <el-menu-item index="/admin" v-if="this.$root.levelid == 2">
        <el-badge value="admin" class="item" type="warning" >管理员</el-badge>
      </el-menu-item>
      <!-- </el-menu-item> -->
    </el-menu>
    <div class="line"></div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      name: "topbar",
      data:this.$root.levelid
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
    },
    logout(){
      this.$confirm('确定退出登录吗？','提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'info'
      }).then(()=>{
        this.$root.levelid = 3;
        this.$message({
          type:'success',
          message:'退出成功'
        });
        setTimeout(()=>{
          this.$router.push('/login')
        })
      }).catch(()=>{
        this.$message({
          type:'info',
          message:'已取消退出'
        })
      })
    }
  },


};
</script>

<style lang="less" scoped>
body {
  .header {
    position: relative;
    .el-menu-nav {
      // padding-right: %;
      li {
        float: right;
        padding-right: 3em;
      }
    }
    .logo {
      position: absolute;
      z-index: 999;
      left: 2em;
      top: 0.7em;
      img {
        width: 2.2em;
        vertical-align: bottom;
      }
      h1 {
        display: inline;
        vertical-align: center;
        color: #fff;
        font-size: 1.7em;
        font-weight: 300;
        font-family: "Courier New", Courier, monospace;
      }
    }
  }
}
</style>

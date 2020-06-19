<template>
  <div class="enrollments">
    <div class="content">
      <el-col :span="8">
        <el-card shadow="always" >
          <el-divider>
            <p>
            <i class="el-icon-star-off" style="font-size:18px"></i>
            欢迎登录星迹
            <i class="el-icon-star-off" style="font-size:18px"></i>
            </p>
          </el-divider>
          <el-form ref="selectForm" :model="selectForm" :rules="rules"  label-width="130px" >
            <el-form-item label="用户名" prop="username">
              <el-input type="text" v-model="selectForm.username" placeholder="" style="width: 188px" required></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="passward" v-model="selectForm.password" auto-complete="off" style="width: 188px" required></el-input>
            </el-form-item>

            <el-form-item label="身份" prop="role">
              <el-select v-model="selectForm.role" filterable placeholder="请选择您的登录身份" style="width: 188px" required>
                <el-option v-for="item in role" :key="item.value" :label="item.role" :value="item.value"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit('selectForm')">登录</el-button>
              <el-button @click="resetForm('selectForm')">重填</el-button>
            </el-form-item>

          </el-form>
          <div style="text-align: center">
          <span v-on:click="ToRegister">没有账号？马上注册</span>
          </div>
        </el-card>
      </el-col>
    </div>
  </div>
</template>
<script>
import qs from "qs";
export default {
  name: "enrollments",
  data() {
    return {
      // name: "liudaxia",
      username: [],
      password: [],
      role: [
        { role: "学生", value: 0 },
        { role: "教师", value: 1 },
        { role: "管理员", value: 2 }
      ],
      selectForm: {
        username: "",
        password: "",
        role: ""
      },
      rules: {
        username: [
          { required: true, message: "请输入宝贝的名字", trigger: "change" },
          {min: 2, max: 10, message: "宝贝名字的长度为2到10个字符喔", trigger: "blur"}

        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "change"
          }
        ],
        role: [
          { required: true, message: "请确认身份", trigger: "change" },
        ]
      }
    };
  },
  methods: {
    onSubmit(formName) {
      //表单验证
      this.$refs[formName].validate(valid => {
        if (valid) {
          var strData = {
            stuUsername: this.selectForm.username,
            stuPassword: this.selectForm.password,
          };

          //学生登录,
          // 分别把下面的url换成各自对应的接口就好
          if (this.selectForm.role == 0) {
            this.axios
              .post("http://localhost:8004/Enrollments/Create", qs.stringify(strData))
              .then(result => {
                if (result.data == 'success') {
                  this.$notify({
                    id: "",
                    title: "登录成功(*￣︶￣)，",
                    message: "登录完成！",
                    position: 'top-left',
                    type: "success"
                  });
                  this.$root.role = this.selectForm.role;
                  this.$root.username = this.selectForm.username;
                  this.$router.push("/students");
                }
              })
              .catch(err => {
                this.$root.role = this.selectForm.role;
                this.$root.username = this.selectForm.username;

                this.$router.push("/students");
                this.$message({
                  message: "登录失败o(╥﹏╥)o"+err+'。',
                  type: "danger"
                });
              });
          }
          //老师登录
          else if (this.selectForm.role == 1) {
            this.axios
              .post("http://localhost:8004/Enrollments/Create", qs.stringify(strData))
              .then(result => {
                if (result.data == 'success') {
                  this.$notify({
                    id: "",
                    title: "登录成功(*￣︶￣)，",
                    message: "登录完成！",
                    position: 'top-left',
                    type: "success"
                  });
                  this.$root.role = this.selectForm.role;
                  this.$root.username = this.selectForm.username;
                  this.$router.push("/coach");
                }
              })
              .catch(err => {
                this.$root.role = this.selectForm.role;
                this.$router.push("/coach");
                this.$message({
                  message: "登录失败o(╥﹏╥)o"+err+'。',
                  type: "danger"
                });
              });

          }
          //管理员登录
          else {
            this.axios
              .post("http://localhost:8004/Enrollments/Create", qs.stringify(strData))
              .then(result => {
                if (result.data == 'success') {
                  this.$notify({
                    id: "",
                    title: "登录成功(*￣︶￣)，",
                    message: "登录完成！",
                    position: 'top-left',
                    type: "success"
                  });
                  this.$root.role = this.selectForm.role;
                  this.$router.push("/admin");
                }
              })
              .catch(err => {
                this.$root.role = this.selectForm.role;
                this.$router.push("/admin");
                this.$message({
                  message: "登录失败o(╥﹏╥)o"+err+'。',
                  type: "danger"
                });
              });
          }
        }
      });
    },

    ToRegister(){
      this.$router.push('/register')
    },

    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    // 点击了浏览器的返回按钮
    goBack () {
      console.log("点击了浏览器的返回按钮");
      this.$root.role=3;
      sessionStorage.clear();
      window.history.back();
    },

  },
  created() {
  },
  mounted() {
    if (window.history && window.history.pushState) {
      // 向历史记录中插入了当前页
      history.pushState(null, null, document.URL);
      window.addEventListener('popstate', this.goBack, false);
    }
  },
  destroyed () {
    window.removeEventListener('popstate', this.goBack, false);
  },
};
</script>
<style lang="less" scoped>
.enrollments {
  background-image: url("/static/images/bgnezha.jpg");
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  min-width: 1000px;
  z-index: -10;
  zoom: 1;
  background-color: rgba(0, 0, 0, 0.5);
  background-repeat: repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -o-background-size: cover;
  background-position: center 0;
  .content {
    display: flex;
    // 调整选课框位置
    margin: 10% 0 0 0;
    justify-content: center;
    overflow: hidden;
  }
  span{cursor:pointer;text-align: center;}
  span:hover{color:#41b883;}

}
</style>

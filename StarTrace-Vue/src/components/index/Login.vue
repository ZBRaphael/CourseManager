<template>
  <div class="enrollments">
    <div class="content">
      <el-col :span="8">
        <el-card shadow="always">
          <el-divider>
            <p>
              <i class="el-icon-star-off" style="font-size:18px"></i>
              欢迎登录星迹
              <i class="el-icon-star-off" style="font-size:18px"></i>
            </p>
          </el-divider>
          <el-form ref="selectForm" :model="selectForm" :rules="rules" label-width="130px">
            <el-form-item label="用户名" prop="username">
              <el-input type="text" v-model="selectForm.username" placeholder="请输入用户名" style="width: 188px"
                        required></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input placeholder="请输入密码" v-model="selectForm.password" style="width: 188px" show-password></el-input>
            </el-form-item>

            <el-form-item label="身份" prop="role">
              <el-select v-model="selectForm.role" filterable placeholder="请选择您的登录身份" style="width: 188px" >
                <el-option
                  v-for="item in role"
                  :key="item.value"
                  :label="item.role"
                  :value="item.value"
                ></el-option>
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
    name: "login",
    data() {
      return {
        name: "liudaxia",
        username: [],
        password: [],
        role: [
          {role: "学生", value: 0},
          {role: "教师", value: 1},
          {role: "管理员", value: 2}
        ],
        selectForm: {
          username: "",
          password: "",
          role: ""
        },
        rules: {
          username: [
            {required: true, message: "请输入您的用户名", trigger: "change"},
            {min: 2, max: 10, message: "长度为2到10个字符", trigger: "blur"}

          ],
          password: [
            {
              required: true,
              message: "请输入您的密码",
              trigger: "change"
            }
          ],
          role: [
            {required: true, message: "请选择您的角色", trigger: "change"},
          ]
        }
      };
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            var strData = {
              stuUsername: this.selectForm.username,
              stuPassword: this.selectForm.password,
            };
            //学生登录,
            if (this.selectForm.role == 0) {
              this.axios
                .post("http://112.124.29.52:8081/stu/login", qs.stringify(strData))
                .then(result => {
                  if (result.data == 'success') {
                    this.$message({
                      message: "登录成功(*￣︶￣)",
                      type: "success"
                    });
                    this.$root.levelid = this.selectForm.role;
                    this.$router.push("/students");
                  } else if (result.data === 'failure') {
                    this.$message({
                      message: "登录失败o(╥﹏╥)o",
                      type: "danger"
                    });
                  }
                })
                .catch(err => {
                  this.$message({
                    message: "登录失败o(╥﹏╥)o" + err + '。',
                    type: "danger"
                  });
                });
            }
            //老师登录
            else if (this.selectForm.role == 1) {
              this.axios
                .post("http://112.124.29.52:8081/tea/login", qs.stringify(strData))
                .then(result => {
                  if (result.data === 'success') {
                    this.$message({
                      message: "登录成功(*￣︶￣)",
                      type: "success"
                    });
                    this.$root.levelid = this.selectForm.role;
                    this.$router.push("/coach");
                  } else if (result.data === 'failure') {
                    this.$message({
                      message: "登录失败o(╥﹏╥)o",
                      type: "danger"
                    });
                  }
                })
                .catch(err => {
                  this.$message({
                    message: "登录失败o(╥﹏╥)o" + err + '。',
                    type: "danger"
                  });
                });

            }
            //管理员登录
            else {
              this.axios
                .post("http://112.124.29.52:8081/admin/login", qs.stringify(strData))
                .then(result => {
                  if (result.data == 'success') {
                    this.$message({
                      message: "登录成功(*￣︶￣)",
                      type: "success"
                    });
                    this.$root.levelid = this.selectForm.role;
                    this.$router.push("/admin");
                  } else if (result.data == 'failure') {
                    this.$message({
                      message: "登录失败o(╥﹏╥)o",
                      type: "danger"
                    });
                  }
                })
                .catch(err => {
                  this.$message({
                    message: "登录失败o(╥﹏╥)o" + err + '。',
                    type: "danger"
                  });
                });
            }
          }
        });
      },

      ToRegister() {
        this.$router.push('/register')
      },
      // 重置表单
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

    },
    created() {
      this.$root.levelid = 3;
    }
  };
</script>
<style lang="less" scoped>
  .enrollments {
    background-image: url("/static/images/background.jpg");
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

    span {
      cursor: pointer;
      text-align: center;
    }

    span:hover {
      color: #41b883;
    }

  }
</style>

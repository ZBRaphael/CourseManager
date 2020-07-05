<template>

  <div class="register">

    <div class="content">
      <el-col :span=8>
        <el-card shadow="always">
          <el-divider>
            <i class="el-icon-s-promotion" style="font-size:18px">欢迎注册星迹系统</i>
          </el-divider>
          <el-form ref="selectForm" :model="selectForm" label-width="130px" :rules="rules">
            <el-form-item label="用户名" prop="stuUsername">
              <el-input type="text" v-model="selectForm.stuUsername" placeholder="" style="width: 188px"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="stuPassword" >
              <el-input type="passward" v-model="selectForm.stuPassword" auto-complete="off" style="width: 188px" show-password></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="stuPhone">
              <!--<el-select v-model="selectForm.courses" filterable placeholder="请选修一本我有的秘籍">-->
              <!--<el-option-->
              <!--v-for="item in courses"-->
              <!--:key="item.Id"-->
              <!--:label="item.Title"-->
              <!--:value="item.Id"-->
              <!--&gt;</el-option>-->
              <!--</el-select>-->
              <el-input type="passward" v-model="selectForm.stuPhone" auto-complete="off" style="width: 188px"></el-input>
            </el-form-item>
            <el-form-item label="兴趣" prop="stuInter">
              <el-select v-model="selectForm.stuInter" filterable placeholder="请选择您的兴趣爱好">
                <el-option
                  v-for="item in stuInter"
                  :key="item.value"
                  :label="item.stuInter"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit">注册</el-button>
              <el-button @click="resetForm('selectForm')">重填</el-button>
            </el-form-item>
            <div style="text-align: center">
            <span v-on:click="ToLogin">已有账号？马上登录</span>
            </div>
          </el-form>
        </el-card>
      </el-col>
    </div>
  </div>
</template>
<script>
  import qs from "qs";
  export default {
    name: "register",
    data() {
      return {
        // name: "lisa",
        stuUsername: [],
        stuPassword: [],
        stuPhone:[],
        stuInter: [
          { stuInter: "篮球课", value: 0 },
          { stuInter: "足球课", value: 1 },
          { stuInter: "体能课", value: 2 },
          { stuInter: "轮滑课", value: 3 },
        ],
        selectForm: {
          stuUsername: "",
          stuPassword: "",
          stuPhone: "",
          stuInter: ""
        },
        rules: {
          stuUsername: [
            { required: true, message: "请输宝贝的名字", trigger: "change" },
            {min: 2, max: 10, message: "长度为2到10个字符", trigger: "blur"}

          ],
          stuPassword: [
            {
              required: true,
              message: "请输入你的密码",
              trigger: "change"
            }
          ],
          stuInter: [
            { required: true, message: "请选择一个兴趣爱好", trigger: "change" }
          ],
          stuPhone: [
            {
              required:true,
              message:"请输入你的联系方式",
              trigger: "change"
            },
            {min: 11, max: 11, message: "请输入11位的手机号码", trigger: "blur"}
          ]

        }
      };
    },
    methods: {
      onSubmit() {
        if (this.selectForm.stuUsername=== "") {
          return this.$message({
            message: "请阁下留下您的名字",
            type: "warning"
          });
        }
        if (this.selectForm.stuPassword === "") {
          return this.$message({
            message: "请填写密码",
            type: "warning"
          });
        }
        if (this.selectForm.stuInter === "" ) {
          return this.$message({
            message: "选择您的方向",
            type: "warning"
          });
        }
        if(this.selectForm.stuPhone === ""){
          return this.$message({
            message:"请填写您的联系方式",
            type: "warning"
          })
        }
        var strData = {
          stuUsername: this.selectForm.stuUsername,
          stuPassword: this.selectForm.stuPassword,
          stuTell:this.selectForm.stuPhone,
          interest: this.stuInter[this.selectForm.stuInter].stuInter,
        };
        this.axios
          .post("http://112.124.29.52:8081/stu/register", qs.stringify(strData))
          .then(result => {
            if (result.data == 'success') {
              this.$message({
                // id: "",
                // title: "注册成功(*￣︶￣)，",
                message: "注册成功，请登录",
                // position: 'top-left',
                type: "success"
              });
              this.$router.push('/login');
              this.resetForm("selectForm");
            }
            else
            {
              this.$message({
                message: "注册失败o(╥﹏╥)o",
                type: "danger"
              });
            }
          })
          .catch(err => {
            this.$message({
              message: "注册失败o(╥﹏╥)o"+err+'。',
              type: "danger"
            });
          });
      },
      ToLogin(){
        this.$router.push('/login')
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
  .register {
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
      margin: 8% 0 0 0;
      justify-content: center;
      overflow: hidden;


    }

    span{cursor:pointer;text-align: center;}
    span:hover{color:#41b883;}
  }
</style>

<template>
  <div class="admin">
    <div class="handle">
      <!-- 左侧内容-->
      <div class="menu">
        <el-timeline>
          <!-- 上课记录查询 -->
          <el-timeline-item placement="top">
            <el-card>
              <h4>上课记录查询</h4>
              <el-form :model="checkForm" ref="checkForm" label-width="6em" class="checkForm">
                <el-row>
                  <el-form-item label="用户名">
                    <el-input placeholder="请输学生名" class="searchName" v-model="searchName" @input="change($event)">
                    </el-input>
                    <el-button type="primary" @click="searchRecord()" icon="el-icon-search" round>搜索</el-button>
                  </el-form-item>
                </el-row>
                <el-row>
                  <el-col :span="13">
                    <el-form-item label="已上总课时" prop="totalHour">
                      <el-input v-model="checkForm.totalHour" placeholder="100" readonly></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="11">
                    <el-form-item label="剩余课时" prop="restHour" readonly>
                      <el-input v-model="checkForm.restHour" placeholder="0" readonly></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-form-item>
                  <el-button type="primary" @click="addHour()">添加课时</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-timeline-item>

          <!-- 添加教练员-->
          <el-timeline-item placement="top">
            <el-card>
              <h4>添加教练员</h4>
              <el-form :model="teaInfo" :rules="rules" ref="teaInfo" label-width="80px" class="adminForm">
                <el-form-item label="用户名" prop="teaUsername" required>
                  <el-input v-model="teaInfo.teaUsername" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="teaPassword" required>
                  <el-input v-model="teaInfo.teaPassword" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="teaTell" required>
                  <el-input v-model="teaInfo.teaTell" placeholder="请输入手机号码"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="addTea('teaInfo')">立即添加</el-button>
                  <el-button @click="resetForm('teaInfo')">重置</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-timeline-item>

          <!--  查看所有教练员名单-->
          <el-timeline-item placement="top">
            <el-card>
              <el-link :underline="false" style="padding-left:8em" @click="handleCheckTea()">
                查看教练员名单
                <i class="el-icon-view el-icon--right"></i>
              </el-link>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>

    <!-- 右侧课程信息大表格-->
    <div class="formData">
      <el-table v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
                :data="studentData.filter(data => !searchName || data.stuUsername.toLowerCase().includes(searchName.toLowerCase()))"
                border style="width: 100%" min-height="650" max-height="650">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column prop="stuUsername" label="学生名" width="100"></el-table-column>
        <el-table-column prop="courseDate" sortable label="上课时间" width="200"></el-table-column>
        <el-table-column prop="courseLocation" label="上课地点" width="150"></el-table-column>
        <el-table-column prop="courseId" label="课程编号" width="100"></el-table-column>
        <el-table-column prop="interest" label="课程类型" width="100"></el-table-column>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <el-tooltip content="点击查看课程详细信息" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="handleCourseInfo(scope.row)" type="warning" size="small">查看</el-button>
            </el-tooltip>
            <el-tooltip content="确定要删除这条上课记录吗？" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="handleDelete(scope.$index, scope.row)" type="danger" size="small" icon="el-icon-delete"
                         circle plain></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加课时对话框-->
      <el-dialog title="添加课时" :visible.sync="dialogFormVisible" class="dialogOne">
        <el-form :model="addClassHourForm" ref="addClassHourForm" class="content">
          <el-form-item label="学生姓名" prop="stuUsername" :label-width="formLabelWidth">
            <el-input v-model="addClassHourForm.stuUsername" autocomplete="off" readonly></el-input>
          </el-form-item>
          <el-form-item label="剩余课时数" prop="restClassHour" :label-width="formLabelWidth" readonly>
            <el-input v-model="addClassHourForm.restClassHour" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="待添加课时" prop="addClassHour" :label-width="formLabelWidth">
            <el-input v-model="addClassHourForm.addClassHour" placeholder="请输入要添加的课时数" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateStudent">确 定</el-button>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 查看课程信息对话框-->
    <el-dialog title="课程信息" :visible.sync="dialogCourseVisible" class="dialogOne">
      <el-form :model="courseForm" class="content" :rules="rules" ref="addClassHourForm">
        <el-form-item label="课程编号" prop="courseId" :label-width="formLabelWidth" required>
          <el-input v-model="courseForm.courseId" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="interest" :label-width="formLabelWidth" required>
          <el-input v-model="courseForm.interest" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="所需课时" prop="courseCostHour" :label-width="formLabelWidth" required>
          <el-input v-model="courseForm.courseCostHour" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="上课日期" required>
          <el-col :span="11">
            <el-form-item prop="date">
              <el-date-picker type="date" placeholder="选择日期" v-model="courseForm.date" style="width: 100%;"
                              readonly></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="上课时段" required>
          <el-col :span="6">
            <el-form-item prop="time1">
              <el-time-picker placeholder="选择上课时间" v-model="courseForm.time1" style="width: 95%;" format="HH:mm"
                              value-format="HH:mm" readonly></el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="6">
            <el-form-item prop="time2">
              <el-time-picker placeholder="选择下课时间" v-model="courseForm.time2" style="width: 95%;" format="HH:mm"
                              value-format="HH:mm" readonly></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="上课地点" prop="courseLocation" :label-width="formLabelWidth" required>
          <el-input v-model="courseForm.courseLocation" autocomplete="off" readonly></el-input>
        </el-form-item>

        <el-form-item label="课程简介" prop="courseDescription" :label-width="formLabelWidth">
          <el-input type="textarea" v-model="courseForm.courseDescription" placeholder="请填写课程信息" maxlength="50"
                    :autosize="{ minRows: 4, maxRows: 6}" show-word-limit readonly></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogCourseVisible = false">确 认</el-button>
      </div>
    </el-dialog>


    <!-- 查看教练员名单 -->
    <el-dialog title="教练员名单" :visible.sync="dialogTableVisible">
      <el-table :data="teaData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="teaId" label="教练员编号" width="150"></el-table-column>
        <el-table-column property="teaUsername" label="教练员用户名" width="150"></el-table-column>
        <el-table-column property="teaHour" label="已上课时数" width="150"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-tooltip content="删除这个教练员？" placement="right" effect="light" :hide-after="2000">
              <el-button
                @click="deleteTea(scope.$index,scope.row)"
                type="danger"
                size="small"
                icon="el-icon-delete"
                circle
                plain
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
  import qs from "qs";

  export default {
    name: "admin",
    data() {
      return {
        name: "lisa",
        searchName: "",
        studentData: [], //全部用户的上课记录信息
        loading: true,
        delay: parseInt(200),

        //搜索用户的已上总课时 剩余课时数
        checkForm: {
          totalHour: "",
          restHour: ""
        },

        // 教练员用户 表单项设置
        teaInfo: {
          teaUsername: "",
          teaPassword: "",
          teaTell: ""
        },

        // 添加课时 表单项
        addClassHourForm: {
          teaUsername: "",
          restClassHour: "",
          addClassHour: ""
        },

        courseForm: {
          courseId: "",
          courseCostHour: "",
          interest: "",
          date: "",
          time1: "",
          time2: "",
          courseLocation: "",
          courseDescription: ""
        },
        // 校验规则
        rules: {
          teaUsername: [
            {required: true, message: "请输入用户名", trigger: "change"},
            {min: 2, max: 10, message: "长度为2到10个字符", trigger: "blur"}
          ],
          teaPassword: [
            {required: true, message: "请输入密码", trigger: "change"},
            {min: 6, max: 15, message: "长度为6到15个字符", trigger: "blur"}
          ],
          teaTell: [
            {required: true, message: "请输入手机号码", trigger: "change"},
            {min: 11, max: 11, message: "再检查一下手机号码有没有输入错误喔～", trigger: "blur"}
          ],
        },

        //添加课时数对话框
        dialogFormVisible: false,
        formLabelWidth: "6em",

        //查看课程信息对话框
        courseInfo: [],
        dialogCourseVisible: false,
        formLabelWidth: "6em",

        //教练员名单
        teaData: [],
        dialogTableVisible: false
      };
    },
    methods: {
      // 重置表单
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //为表格添加序号
      indexMethod(index) {
        return index + 1;
      },
      //日期转换
      getDateOne(time) {
        var timeArr = time.split(" ");
        var d = timeArr[0].split("-");
        return new Date(d[0], d[1] - 1, d[2]);
      },
      getDateTwo(time) {
        var timeArr = time.split(" ");
        var t = timeArr[1].split("-");
        var h = t[0].split(":");
        return h[0] + ":" + h[1];
      },
      getDateThree(time) {
        var timeArr = time.split(" ");
        var t = timeArr[1].split("-");
        var h = t[1].split(":");
        return h[0] + ":" + h[1];

      },

      // 获得搜索学生的已上总课时数和剩余课时数
      searchRecord() {

        //todo 向后端请求用户名为searchName的学生的已上总课时和剩余课时
        // this.axios.get("http://localhost:8004/Courses/Edit?id=" + item.CourseId).then(result => {
        //   if (result.status == 200) {
        //     this.checkForm.totalHour = result.data.stuTotalClassHour;
        //     this.checkForm.restHour = result.data.stuTotalClassHour;
        //   }
        // });
        var result = {
          stuTotalClassHour: '7',
          stuRemainingClassHour: '2',
        }
        this.checkForm.totalHour = result.stuTotalClassHour;
        this.checkForm.restHour = result.stuRemainingClassHour;
      },

      //添加课时数
      addHour() {
        //回显
        this.dialogFormVisible = true;
        this.addClassHourForm.stuUsername = this.searchName;
        this.addClassHourForm.restClassHour = this.checkForm.restHour;
      },
      updateStudent() {
        //关闭对话框
        //this.dialogFormVisible = false;
        var updateData = {
          stuUsername: this.addClassHourForm.stuUsername,
          addHourNumber: this.addClassHourForm.addClassHour
        };
        // 改变post的编码格式，适应后台做修改！
        //todo 传学生姓名和要添加的课时数即updateData给后端
        this.axios
          .post("http://localhost:8004/Students/Edit", qs.stringify(updateData))
          .then(result => {
            if (result.status == 200 || result.status == 302) {
              this.$notify({
                id: "",
                title: "修改成功",
                message: "添加成功！请注意查看。",
                type: "success"
              });
              this.searchRecord();
              this.addClassHourForm.restClassHour = this.checkForm.restHour;
              this.resetForm('addClassHourForm');

            }
          })
          .catch(err => {
            this.$message({
              message: "添加失败o(╥﹏╥)o。" + err + '。',
              type: "danger"
            });
          });
      },

      // 添加教练员用户
      addTea(formName) {
        // console.log(formName);
        this.$refs[formName].validate(valid => {
          if (valid) {
            // 表单验证成功
            var strData = {
              teaUsername: this.teaInfo.teaUsername,
              teaPassword: this.teaInfo.teaPassword,
              teaTell: this.teaInfo.teaTell,
            };
            console.log(strData);
            // 改变post的编码格式，适应后台
            this.axios
              .post(
                "http://localhost:8004/Students/create",
                qs.stringify(strData)
              )
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$message({
                    message: "添加成功(*￣︶￣)，",
                    type: "success"
                  });
                  this.getStudentData();
                  this.resetForm("teaInfo");
                }
              })
              .catch(err => {
                console.log(err)
                this.$message({
                  message: "添加失败o(╥﹏╥)o，" + err + '。',
                  type: "danger"
                });
              });
          } else {
            this.$message.error("阁下填写不完整喔！我快马加鞭前来提示！");
            // console.log("error submit!!");
            return false;
          }
        });
      },

      // 查看课程详细信息
      handleCourseInfo(row) {
        this.dialogCourseVisible = true;

        this.courseForm.courseId = row.courseId;
        this.courseForm.interest = row.interest;
        this.courseForm.date = this.getDateOne(row.courseDate);
        this.courseForm.time1 = this.getDateTwo(row.courseDate);
        this.courseForm.time2 = this.getDateThree(row.courseDate);
        this.courseForm.courseLocation = row.courseLocation;
        this.courseForm.courseCostHour = row.courseCostHour;
        this.courseForm.courseDescription = row.courseDescription;
        //T 将课程编号courseId传递给 获取该课程的详细信息
        // this.$http.get("http://localhost:8004/Students").then(result => {
        //   if (result.status == 200) {
        //     // console.log(result.data);
        //     this.studentData = result.data;
        //
        //   }
        // });
        // var result = {
        //   courseId: '1',
        //   courseCostHour: '1',
        //   courseDescription: '篮球基本技巧讲解及实践课',
        // };
        // this.courseForm.courseCostHour = result.courseCostHour;
        // this.courseForm.courseDescription = result.courseDescription;
      },

      // 查看教练员名单
      handleCheckTea() {
        this.dialogTableVisible = true;
        this.getTeaData();
      },
      getTeaData() {
        //todo 获取在职教练员名单 包括教练员编号、名字、已上课时
        // this.axios.get("http://localhost:8004/Courses/Edit?id=").then(result=>{
        //   console.log(result.data);
        //   this.teaData=result.data;
        // });
        this.teaData = [
          {
            teaId: '14758',
            teaUsername: '张二',
            teaTotalClassHour: '4',

          },
          {
            teaId: '21209',
            teaUsername: '李一',
            teaTotalClassHour: '4'

          }];
      },

      // 删除教练员
      deleteTea(index, row) {
        //todo 传 教练员编号 给后端
        this.$confirm("此操作将从系统中删除该教练员, 是否继续?", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "warning"
        })
          .then(() => {
            this.axios
              .get("http://localhost:8004/Enrollments/Delete?id=" + row.teaId)
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$message({
                    message: "删除成功(*￣︶￣)，",
                    type: "success"
                  });
                  this.getTeaData();
                }
              })
              .catch(err => {
                this.$message({
                  message: "删除失败o(╥﹏╥)o" + err + '。',
                  type: "danger"
                });
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      },

      // 删除该条上课记录
      handleDelete(index, row) {
        console.log(row.CourseId);
        this.$confirm("此操作将删除该用户的上课记录, 是否继续?", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "warning"
        })
          .then(() => {
            this.axios
              .get("http://localhost:8004/Students/Delete?StuId=" + row.StuId + '&courseId=' + row.CourseId)
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$message({
                    message: "删除成功(*￣︶￣)",
                    type: "success"
                  });
                  this.getStudentData();
                }
              })
              .catch(err => {
                this.$message({
                  message: "删除失败o(╥﹏╥)o，" + err + '。',
                  type: "danger"
                });
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      },

      // 获取全部学生数据
      getStudentData() {
        //todo
        // this.$http.get("http://localhost:8004/Students").then(result => {
        //   if (result.status == 200) {
        //     // console.log(result.data);
        //     this.studentData = result.data;
        //     this.loading = false;
        //   }
        // });
        this.studentData =
          [{
            courseId: '1',
            interest: '篮球课',
            courseCostHour: '1',
            courseDate: '2020-06-13 15:00-17:00',
            courseLocation: '理工体育馆',
            stuUsername: '张三',
            // Enrollments: [{
            //   stuId: '11',
            //   stuUsername: 'www',
            //   stuTell: '110'
            // }],
            courseDescription: '篮球基本技巧讲解及实践课'
          },
            {
              courseId: '12',
              interest: '足球课',
              courseCostHour: '2',
              courseDate: '2020-06-12 19:00-21:00',
              courseLocation: '人大体育馆',
              stuUsername: '李四',
              // Enrollments: [{
              //   stuId: '12',
              //   stuUsername: 'wy',
              //   stuTell: '120'
              // },
              //   {
              //     stuId: '3',
              //     stuUsername: 'yy',
              //     stuTell: '119'
              //   }
              // ],
              courseDescription: '防守教学'
            }
          ];
        this.loading = false;
      },

    },
    //页面数据初始化
    // 增加loading
    mounted() {
      this.getStudentData();
    }
  };
</script>
<style lang="less" scoped>
  .admin {
    display: flex;
    height: 42em;

    .handle {
      flex: 2;

      .menu {
        padding: 5% 10% 0 10%;

        h4 {
          margin: 0.2em;
          padding-bottom: 0.5em;
        }

        .adminForm {
          .searchName {
            width: 70%;
          }

          .el-form-item__content {
            /*margin: 0 !important;*/

            .el-input__suffix {
              right: 0 !important;
            }

            .el-input {
              width: 70%;
            }
          }
        }

        .checkForm {
          .searchName {
            width: 40%;
          }

          .el-form-item__content {
            // margin: 0 !important;
            .el-input__suffix {
              right: 0 !important;
            }

            .el-input {
              width: 60%;
            }
          }
        }
      }
    }

    .formData {
      flex: 3;
      text-align: center;

      .el-table {
        text-align: center !important;
        float: right;
        // padding: 5%
        margin: 2%;
      }

      // background-color: lightpink;
    }

    .dialogOne {
      .el-form-item__content {
        margin: 0 !important;

        .el-input__suffix {
          right: 0 !important;
        }

        .el-input {
          width: 70%;
        }
      }
    }
  }
</style>

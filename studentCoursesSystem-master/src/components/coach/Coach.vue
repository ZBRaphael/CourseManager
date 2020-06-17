<template>
  <div class="coach">
    <!-- <h2>这里是教练员用户界面啊啊啊啊</h2> -->
    <div class="handle">
      <!-- 左侧 -->
      <div class="menu">
        <el-timeline>
          <el-timeline-item placement="top">
            <el-card>
              <h4>课程查询</h4>
              <el-input placeholder="请输入课程名或上课地点" class="searchName" v-model="searchName">
              </el-input>
              <el-button type="primary" icon="el-icon-search" round>搜索</el-button>
            </el-card>
          </el-timeline-item>
          <el-timeline-item placement="top">
            <el-card>
              <h4>添加课程</h4>
              <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px" class="coursesForm">
                <el-form-item label="课程类型" prop="interest" required>
                  <el-select v-model="ruleForm.interest" filterable placeholder="请选择课程类型">
                    <el-option v-for="item in interest" :key="item.value" :label="item.interest" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="课 时 数" prop="courseCostHour" required>
                  <el-input v-model="ruleForm.courseCostHour" placeholder="请输入本节课需花费的课时数"></el-input>
                </el-form-item>
                <el-form-item label="上课日期" required>
                  <el-col :span="11">
                    <el-form-item prop="date">
                      <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date" style="width: 100%;">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                </el-form-item>
                <el-form-item label="上课时段" required>
                  <el-col :span="11">
                    <el-form-item prop="time1">
                      <el-time-picker placeholder="选择上课时间" v-model="ruleForm.time1" style="width: 100%;" format="HH:mm" value-format="HH:mm"></el-time-picker>
                    </el-form-item>
                  </el-col>
                  <el-col class="line" :span="1">-</el-col>
                  <el-col :span="11">
                    <el-form-item prop="time2">
                      <el-time-picker placeholder="选择下课时间" v-model="ruleForm.time2" style="width: 100%;" format="HH:mm" value-format="HH:mm"></el-time-picker>
                    </el-form-item>
                  </el-col>
                </el-form-item>
                <el-form-item label="上课地点" prop="courseLocation">
                  <el-input v-model="ruleForm.courseLocation" placeholder="请输入上课地点"></el-input>
                </el-form-item>
                <el-form-item label="课程简介" prop="courseDescription">
                  <el-input
                    type="textarea"
                    v-model="ruleForm.courseDescription"
                    placeholder="请填写课程信息"
                    maxlength="50"
                    :autosize="{ minRows: 4, maxRows: 6}"
                    show-word-limit
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm('ruleForm')">立即添加</el-button>
                  <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-timeline-item>
          <el-timeline-item placement="top">
            <el-card>
              <el-link :underline="false" style="padding-left:8em" @click="handleCheckCourses">
                查看上课记录
                <i class="el-icon-view el-icon--right"></i>
              </el-link>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>

    <!-- 右侧教练员已发布的课程信息表格-->
    <div class="formData">
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="coursesData.filter(data => !searchName || data.interest.toLowerCase().includes(searchName.toLowerCase())|| data.courseLocation.toLowerCase().includes(searchName.toLowerCase()))"
        border
        style="width: 100%"
        min-height="650"
        max-height="650"
      >
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="courseId" label="课程编号" width="80"></el-table-column>
        <el-table-column prop="interest" label="课程类型" width="100"></el-table-column>
        <el-table-column prop="courseDate" label="上课时间" sortable width="200"></el-table-column>
        <el-table-column prop="courseLocation" label="上课地点" width="150"></el-table-column>
        <el-table-column label="选课人数" width="120">
          <template slot-scope="scope">
            <el-tooltip content="选课表中查看" placement="top" effect="light" :hide-after="2000">
              <el-button @click="handleCourses(scope.row)" type="success" size="small">
                已选
                <strong>{{scope.row.Enrollments.length}}</strong>
                人
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <el-tooltip content="修改课程信息" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="handleClick(scope.row)" type="warning" size="small">修改</el-button>
            </el-tooltip>
            <el-tooltip content="确定要取消发布该课程吗" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="handleDelete(scope.$index, scope.row)" type="danger" size="small" icon="el-icon-delete"
                         circle plain></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>


      <!-- 查看和修改课程信息-->
      <el-dialog title="课程信息" :visible.sync="dialogFormVisible" class="dialogOne">
        <el-form :model="updateForm" class="content" :rules="rules" ref="updateForm">
          <el-form-item label="课程编号" prop="courseId" :label-width="formLabelWidth" required>
            <el-input v-model="updateForm.courseId" autocomplete="off" readonly></el-input>
          </el-form-item><el-form-item label="课程类型" prop="interest" :label-width="formLabelWidth" required>
            <el-select v-model="updateForm.interest" filterable placeholder="请选择课程类型">
              <el-option v-for="item in interest" :key="item.value" :label="item.interest" :value="item.value" ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所需课时" prop="courseCostHour" :label-width="formLabelWidth" required>
            <el-input v-model="updateForm.courseCostHour" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="上课日期" required>
            <el-col :span="11">
              <el-form-item prop="date">
                <el-date-picker type="date" placeholder="选择日期" v-model="updateForm.date" style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="上课时段" required>
            <el-col :span="6">
              <el-form-item prop="time1">
                <el-time-picker placeholder="选择上课时间" v-model="updateForm.time1" style="width: 95%;" format="HH:mm" value-format="HH:mm"></el-time-picker>
              </el-form-item>
            </el-col>
            <el-col class="line" :span="1">-</el-col>
            <el-col :span="6">
              <el-form-item prop="time2">
                <el-time-picker
                  placeholder="选择下课时间"
                  v-model="updateForm.time2"
                  style="width: 95%;"
                  format="HH:mm"
                  value-format="HH:mm"
                ></el-time-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="上课地点" prop="courseLocation" :label-width="formLabelWidth" required>
            <el-input v-model="updateForm.courseLocation" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="课程简介" prop="courseDescription" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              v-model="updateForm.courseDescription"
              placeholder="请填写课程信息"
              maxlength="50"
              :autosize="{ minRows: 4, maxRows: 6}"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateStudent('updateForm')">确认修改</el-button>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
        </div>
      </el-dialog>
<!--    </div>-->

    <!-- 查看选课人数具体信息-->
    <el-dialog title="选课学生信息表" :visible.sync="dialogTableVisible">
      <el-table :data="stuChooseData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="courseId" label="课程编号" width="100"></el-table-column>
        <el-table-column property="stuUsername" label="学生姓名" width="100"></el-table-column>
        <el-table-column property="stuTell" label="联系方式" width="150"></el-table-column>
        <el-table-column property="isAttend" label="是否签到" width="100" class-name="status-col">
          <template slot-scope="{row}">
            <el-tag :type="[row.isAttend==='未签到' ? 'danger':'success']">
              {{ row.isAttend }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip content="删除该学生的选课？" placement="right" effect="light" :hide-after="2000">
              <el-button @click="deleteCourse(scope.$index,scope.row)" type="danger" size="small" icon="el-icon-delete"
                         circle plain></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 查看教练员上课记录 -->
    <el-dialog title="上课记录表" :visible.sync="dialogTeaClassFormVisible">
      <el-table :data="teaCourseData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="courseId" label="课程编号" width="100"></el-table-column>
        <el-table-column property="interest" label="课程类型" width="100"></el-table-column>
        <el-table-column property="courseDate" label="上课时间" sortable width="200"></el-table-column>
        <el-table-column property="courseLocation" label="上课地点" width="150"></el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>
<script>
  import qs from "qs";

  export default {
    name: "coach",

    data() {
      return {
        name: "lisa",
        searchName: "",
        coursesData: [],
        loading: true,

        interest: [
          {interest: "篮球课", value: 0},
          {interest: "足球课", value: 1},
          {interest: "体能班", value: 2},
          {interest: "轮滑课", value: 3},
        ],


        // 添加课程 表单项设置
        ruleForm: {
          interest: "",
          courseCostHour: "",
          date: "",
          time1: "",
          time2: "",
          courseLocation: "",
          courseDescription: ""
        },
        // 修改课程信息 表单项设置
        updateForm: {
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
          interest: [{required: true, message: "请选择具体的课程类型", trigger: "blur"}],
          courseCostHour: [{required: true, message: "请输入具体的课时数", trigger: "blur"}],
          date: [{required: true, message: "请选择具体的日期", trigger: "blur"}],
          time1: [{required: true, message: "请选择课程开始时间", trigger: "blur"}],
          time2: [{required: true, message: "请选择课程结束时间", trigger: "blur"}],
          courseLocation: [{required: true, message: "请输入具体的上课地点", trigger: "blur"}],
          courseDescription: [{required: true, message: "请填写课程信息,否则无法通过喔~", trigger: "change"}]
        },
        dialogFormVisible: false,
        formLabelWidth: "6em",

        //存储选课学生的信息
        stuChooseData: [],
        dialogTableVisible: false,

        //存储教练员的上课记录
        teaCourseData: [],
        dialogTeaClassFormVisible: false
      };
    },
    methods: {
      // 重置表单
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      // 为表格添加序号
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
      // 将新添加课程的上课时间进行格式转换（注：data是日期 date2是时间）
      sendAddTime(date, date2) {
        var Y = date.getFullYear();
        var M =
          date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1;
        var MT = parseInt(M) + 1;
        var D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var addTime = Y + "-" + M + "-" + D + " " + date2 + ":00";
        console.log(addTime);
        return addTime;
      },

      // 教练员发布课程
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            // 表单验证成功
            var strData = {
              interest: this.ruleForm.interest,
              courseCostHour: this.ruleForm.courseCostHour,
              courseStartDate: this.sendAddTime(
                this.ruleForm.date,
                this.ruleForm.time1
              ),
              courseEndDate: this.sendAddTime(
                this.ruleForm.date,
                this.ruleForm.time2
              ),
              courseLocation: this.ruleForm.courseLocation,
              courseDescription: this.ruleForm.courseDescription
            };
            // 改变post的编码格式，适应后台
            //todo 提交课程信息strData到后端
            this.axios
              .post("http://localhost:8004/Courses/create", qs.stringify(strData))
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$message({
                    message: "添加成功(*￣︶￣)，",
                    type: "success"
                  });
                  this.getAllcourses();
                }
              })
              .catch(err => {
                this.$message({
                  message: "添加失败o(╥﹏╥)o",
                  type: "danger"
                });
              });
          } else {
            this.$message.error("填写不完整喔！我快马加鞭前来提示！");
            return false;
          }
        });
      },

      // 取消发布 删除课程
      handleDelete(index, row) {
        this.$confirm("此操作将删除该课程所有信息, 是否继续?", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "warning"
        })
          .then(() => {
            //TODO 后端要把该课程的isCanceledBTea修改为1
            this.axios
              .get("http://localhost:8004/Courses/Delete?id=" + row.courseId)
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$message({
                    message: "删除成功(*￣︶￣)，",
                    type: "success"
                  });
                  this.getAllcourses();
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

      // 删除某学生的选课
      deleteCourse(index, row) {
        this.$confirm("此操作将删除该学生的这条选课记录, 是否仍要扇窗户?", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "warning"
        })
          .then(() => {
            //todo 删除 课程编号为row.courseID 学生姓名为row.stuUsername 的选课记录

            this.axios
              .get("http://localhost:8004/Courses/Delete?courseid=" + row.courseId + "&stuUsername=" + row.stuUsername)
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$message({
                    message: "删除成功(*￣︶￣)，",
                    type: "success"
                  });
                  this.getAllcourses();
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

      //选课表查看选课学生们的信息
      handleCourses(row) {
        this.dialogTableVisible = true;
        this.stuChooseData = []
        row.Enrollments.forEach(item => {
          var newCourse = {
            courseId: row.courseId,
            stuUsername: item.stuUsername,
            stuTell: item.stuTell,
            isAttend: item.isAttend==='0'? '未签到':'已签到'
          };
          this.stuChooseData.push(newCourse);
        });
      },

      //查看课程详细信息
      handleClick(row) {
        // 数据回显
        // this.resetForm(updateForm);
        this.updateForm.courseId = row.courseId;
        this.updateForm.interest = row.interest;
        this.updateForm.courseCostHour = row.courseCostHour;
        this.updateForm.courseDescription = row.courseDescription;
        this.updateForm.courseLocation = row.courseLocation;
        this.updateForm.date = this.getDateOne(row.courseDate);
        this.updateForm.time1 = this.getDateTwo(row.courseDate);
        this.updateForm.time2 = this.getDateThree(row.courseDate);

        this.dialogFormVisible = true;
      },

      //查看自己的上课记录
      handleCheckCourses() {
        this.dialogTeaClassFormVisible = true;
        //todo 向后端获取这个教练员的上课记录信息
        // this.$http.get("http://localhost:8004/Students").then(result => {
        //   if (result.status == 200) {
        //     this.teaCourseData = [];
        //     result.forEach(item => {
        //       var teaCourse = {
        //         courseId: item.courseId,
        //         interest: item.interest,
        //         courseDate: item.courseDate,
        //         courseLocation: item.courseLocation,
        //       };
        //       this.teaCourseData.push(teaCourse);
        //     });
        //   }
        // });
        var result = [
          {
            courseId: '1',
            interest: '篮球课',
            courseDate: '2020-06-13 15:00-17:00',
            courseLocation: '理工体育馆',
          },
          {
            courseId: '12',
            interest: '足球课',
            courseDate: '2020-06-12 19:00-21:00',
            courseLocation: '人大体育馆',
          }
        ];
        this.teaCourseData = [];
        result.forEach(item => {
          var teaCourse = {
            courseId: item.courseId,
            interest: item.interest,
            courseDate: item.courseDate,
            courseLocation: item.courseLocation,
          };
          this.teaCourseData.push(teaCourse);
        });
      },

      // 修改课程信息
      updateStudent(formName) {
        console.log(formName+'hi');
        this.$refs[formName].validate(valid => {
          if (valid) {
            var updateData = {
              courseId: this.updateForm.courseId,
              interest: this.updateForm.interest,
              courseCostHour: this.updateForm.courseCostHour,
              courseStartDate: this.sendAddTime(this.updateForm.date, this.updateForm.time1),
              courseEndDate: this.sendAddTime(this.updateForm.date, this.updateForm.time2),
              courseLocation: this.updateForm.courseLocation,
              courseDescription: this.updateForm.courseDescription
            };
            this.dialogFormVisible = false;
            //TODO 将更新后的课程信息updateData提交给后端 更新数据库的记录
            this.axios
              .post("http://localhost:8004/Students/Edit", qs.stringify(updateData))
              .then(result => {
                if (result.status == 200 || result.status == 302) {
                  this.$notify({
                    id: "",
                    title: "修改成功",
                    message: "修改成功！请注意查看。",
                    type: "success"
                  });
                  this.getAllcourses();
                }
              })
              .catch(err => {
                this.$message({
                  message: "修改失败o(╥﹏╥)o。" + err + '。',
                  type: "danger"
                });
              });
          }
          else {
            this.$message.error("阁下网络错误！刘大侠我快马加鞭前来提示！");
            return false;
          }
        });
      },

      // 获取全部已发布的课程信息
      getAllcourses() {

        // this.axios.get("http://localhost:8004/Courses").then(result => {
        //   if (result.status == 200) {
        //     this.coursesData = result.data;
        //     this.loading = false;
        //   }
        // });

        this.coursesData =
          [{
            courseId: '1',
            interest: '篮球课',
            courseCostHour: '1',
            courseDate: '2020-06-13 15:00-17:00',
            courseLocation: '理工体育馆',
            isCanceledByTea: '0',
            Enrollments: [{
              stuId: '11',
              stuUsername: 'www',
              stuTell: '110',
              isAttend:'0',
            }],
            courseDescription: '篮球基本技巧讲解及实践课'
          },
            {
              courseId: '12',
              interest: '足球课',
              courseCostHour: '2',
              courseDate: '2020-06-12 19:00-21:00',
              courseLocation: '人大体育馆',
              isCanceledByTea: '0',
              Enrollments: [{
                stuId: '12',
                stuUsername: 'wy',
                stuTell: '120',
                isAttend:'0',
              },
                {
                  stuId: '3',
                  stuUsername: 'yy',
                  stuTell: '119',
                  isAttend:'1'
                }
              ],
              courseDescription: '防守教学',
              isAttend:'1',
            }
          ];
        this.loading = false;
      },

    },
    mounted() {
      this.getAllcourses();
    }
  };
</script>
<style lang="less" scoped>
  .coach {
    display: flex;
    height: 42em;

    .handle {
      flex: 2;
      // height: 50em;
      // background-color: lightblue;
      .searchName {
        width: 70%;
      }

      .menu {
        padding: 5% 10% 0 10%;

        h4 {
          margin: 0.2em;
          padding-bottom: 0.5em;
        }

        .studentForm {
          .el-form-item__content {
            // margin: 0 !important;
            .el-input__suffix {
              right: 0 !important;
            }

            .el-input {
              width: 70%;
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

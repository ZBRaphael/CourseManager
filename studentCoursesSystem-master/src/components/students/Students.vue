<template>
  <div class="student">
    <!-- <h2>这里是学生用户界面啊啊啊啊</h2> -->
    <div class="handle">
      <!-- 左侧的东西 -->
      <div class="menu">
        <el-timeline>
          <!--课程查询询-->
          <el-timeline-item placement="top">
            <el-card>
              <h4>课程查询</h4>
              <el-input placeholder="请输入课程名或上课地点" class="searchName" v-model="searchName">
              </el-input>
              <el-button type="primary" icon="el-icon-search" round>搜索</el-button>
            </el-card>
          </el-timeline-item>
          <!-- 查看学生个人信息-->
          <el-timeline-item placement="top">
            <el-card>
              <h4>个人信息</h4>
              <el-form :model="stuInfo" ref="ruleForm" label-width="80px" class="studentForm">
                <el-form-item label="用户名" prop="stuUsername" :label-width="formLabelWidth">
                  <el-input v-model="stuInfo.stuUsername" readonly></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="stuTell" :label-width="formLabelWidth">
                  <el-input v-model="stuInfo.stuTell" readonly></el-input>
                </el-form-item>
                <el-form-item label="已上总课时" prop="stuTotalHour" :label-width="formLabelWidth">
                  <el-input v-model="stuInfo.stuTotalHour" readonly></el-input>
                </el-form-item>
                <el-form-item label="剩余课时" prop="stuRestHour" :label-width="formLabelWidth">
                  <el-input v-model="stuInfo.stuRestHour" readonly></el-input>
                </el-form-item>
                <el-form-item :label-width="formLabelWidth">
                  <el-button type="primary" @click="beforModifyStuInfo">修改个人信息</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-timeline-item>
          <!-- 查看自己的上课记录-->
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
        :data="coursesData.filter(data => !searchName || data.Title.toLowerCase().includes(searchName.toLowerCase())|| data.Place.toLowerCase().includes(searchName.toLowerCase()))"
        border
        style="width: 100%"
        min-height="650"
        max-height="650"
      >
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column prop="courseLocation" label="上课地点" width="120"></el-table-column>
        <el-table-column prop="courseDate" label="上课时间" sortable width="200"></el-table-column>
        <el-table-column label="课程名" width="100">
          <template slot-scope="scope">
            <el-tooltip content="点击可以查看详细的课程信息喔～" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="checkCourseInfo(scope.row)" type="primary" size="small">
                <strong>{{scope.row.interest}}</strong>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="state" label="课程状态" width="100" align="center" class-name="status-col">
          <template slot-scope="{row}">
            <!--            <el-tag :type="row.isAttend | statusFilter">-->
            <el-tag :type="row.state==='待签到' ? 'danger':row.state==='已签到'? 'success':'gray'">
              {{ row.state }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="选课人数" width="120">
          <template slot-scope="scope">
            <el-tooltip content="点击可以看看有哪些小朋友在上这节喔～" placement="top" effect="light" :hide-after="2000">
              <el-button @click="handleCourses(scope.row)" type="primary" size="small">
                已选
                <strong>{{scope.row.Enrollments.length}}</strong>
                人
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
          <template slot-scope="{row}">
            <el-tooltip content="记得要仔细查看上课时间和地点喔～" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button v-if="row.state==='未选课'" size="mini" type="primary" @click="modifyStatus(row,'待签到')">
                选课
              </el-button>
            </el-tooltip>
            <el-tooltip content="课程开始前记得来签到呀～" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button v-if="row.state==='待签到'" size="mini" type="warning" @click="modifyStatus(row,'已签到')">
                签到
              </el-button>
            </el-tooltip>
            <el-tooltip content="确定不上这节课了吗o(╥﹏╥)o" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button v-if="(row.state==='待签到')" size="mini" type="danger" @click="modifyStatus(row,'未选课')">
                取消选课
              </el-button>
            </el-tooltip>
            <el-tooltip content="这节课已经完成签到啦(*￣︶￣)" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button v-if="row.state==='已签到'" size="mini" type="success" @click="modifyStatus(row,'已签到')">
                已签到
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!--      查看课程信息-->
      <el-dialog title="课程信息" :visible.sync="courseInfoVisible">
        <el-form :model="courseInfo" class="content" ref="updateForm">
          <el-form-item label="课程编号" prop="courseId" :label-width="formLabelWidth" required>
            <el-input v-model="courseInfo.courseId" autocomplete="off" readonly></el-input>
          </el-form-item>
          <el-form-item label="课程名" prop="interest" :label-width="formLabelWidth" required>
            <el-input v-model="courseInfo.interest" autocomplete="off" readonly></el-input>
          </el-form-item>
          <el-form-item label="课时数" prop="courseCostHour" :label-width="formLabelWidth" required>
            <el-input v-model="courseInfo.courseCostHour" autocomplete="off" readonly></el-input>
          </el-form-item>
          <el-form-item label="上课时间" :label-width="formLabelWidth" required>
            <el-col :span="5">
              <el-form-item prop="date">
                <el-date-picker type="date" placeholder="选择日期" v-model="courseInfo.date" style="width: 100%;"
                                readonly></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="5">
              <el-form-item prop="time1">
                <el-time-picker placeholder="选择时间" v-model="courseInfo.time1" style="width: 100%;" format="HH:mm"
                                value-format="HH:mm" readonly></el-time-picker>
              </el-form-item>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="5">
              <el-form-item prop="time2">
                <el-time-picker placeholder="选择时间" v-model="courseInfo.time2" style="width: 100%;" format="HH:mm"
                                value-format="HH:mm" readonly></el-time-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="上课地点" prop="courseLocation" :label-width="formLabelWidth" required>
            <el-input v-model="courseInfo.courseLocation" autocomplete="off" readonly></el-input>
          </el-form-item>
          <el-form-item label="课程简介" prop="courseDescription" :label-width="formLabelWidth">
            <el-input type="textarea" v-model="courseInfo.courseDescription" placeholder="请填写课程信息" maxlength="50"
                      :autosize="{ minRows: 4, maxRows: 6}" show-word-limit readonly></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="courseInfoVisible = false">确 定</el-button>
        </div>
      </el-dialog>

    </div>

    <!-- 查看选了某节课的学生信息-->
    <el-dialog title="选课学生信息表" :visible.sync="stuChooseFormVisible" class="dialogOne">
      <el-table :data="stuChooseData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="stuUsername" label="学生姓名"></el-table-column>
      </el-table>
    </el-dialog>

    <!-- 修改个人信息 -->
    <el-dialog title="修改个人信息" :rules="rules" :visible.sync="updateStuInfoDialogVisible" class="dialogOne">
      <el-form :model="updateStuInfo" class="content" :rules="rules" ref="updateStuInfo">
        <el-form-item label="用户名" prop="stuUsername" :label-width="formLabelWidth">
          <el-input v-model="updateStuInfo.stuUsername" autocomplete="off" required></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="stuTell" :label-width="formLabelWidth">
          <el-input v-model="updateStuInfo.stuTell" autocomplete="off" required></el-input>
        </el-form-item>
        <el-form-item label="修改密码" prop="stuPassword" :label-width="formLabelWidth">
          <el-input v-model="updateStuInfo.stuPassword" placeholder="请输入新密码..." autocomplete="off" required></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword" :label-width="formLabelWidth">
          <el-input v-model="updateStuInfo.confirmPassword" placeholder="请再次输入密码..." autocomplete="off"
                    required></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyStuInfo">确认修改</el-button>
        <el-button @click="updateStuInfoDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看自己的上课记录 -->
    <el-dialog title="上课记录表" :visible.sync="dialogStuClassFormVisible" class="dialogOne">
      <el-table :data="stuCourseData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="courseId" label="课程编号" width="100"></el-table-column>
        <el-table-column property="interest" label="课程名" width="100"></el-table-column>
        <el-table-column property="courseDate" label="上课时间" sortable width="200"></el-table-column>
        <el-table-column property="courseLocation" label="上课地点" width="150"></el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>
<script>
  import qs from "qs";

  export default {
    name: "student",
    data() {
      return {
        name: "lisa",
        searchName: "",
        loading: true,

        // 校验规则
        rules: {
          stuUsername: [
            {required: true, message: "请输入用户名", trigger: "change"},
            {min: 2, max: 10, message: "长度为2到10个字符", trigger: "blur"}
          ],
          stuPassword: [
            {required: true, message: "请输入密码", trigger: "change"},
            {min: 6, max: 15, message: "长度为6到15个字符", trigger: "blur"}
          ],
          confirmPassword: [
            {required: true, message: "请输入密码", trigger: "change"},
            {min: 6, max: 15, message: "两次密码需保持一致，长度为6到15个字符", trigger: "blur"}
          ],
          stuTell: [
            {required: true, message: "请输入手机号码", trigger: "change"},
            {min: 11, max: 11, message: "再检查一下手机号码有没有输入错误喔～", trigger: "blur"}
          ],
        },


        // 学生个人信息 表单项设置
        stuInfo: {
          stuUsername: '',
          stuTell: '',
          stuTotalHour: '',
          stuRestHour: ''
        },

        // 修改学生个人信息信息
        updateStuInfoDialogVisible: false,
        updateStuInfo: {
          stuUsername: '',
          stuTell: '',
          stuPassword: '',
          confirmPassword: '',
        },

        //最近发布的课程信息 总表
        coursesData: [],
        courseInfoVisible: false,
        formLabelWidth: "7em",

        // 某节课详细的课程信息 表单项设置
        courseInfo: {
          courseId: "",
          courseCostHour: "",
          interest: "",
          date: "",
          time1: "",
          time2: "",
          courseLocation: "",
          courseDescription: ""
        },

        //选了某节课的全部选课学生的信息
        stuChooseData: [],
        stuChooseFormVisible: false,

        //学生的上课记录
        stuCourseData: [],
        dialogStuClassFormVisible: false,

      };
    },
    methods: {
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

      // 修改学生个人信息
      beforModifyStuInfo() {
        this.updateStuInfoDialogVisible = true;
        //回显
        this.updateStuInfo.stuUsername = this.stuInfo.stuUsername;
        this.updateStuInfo.stuTell = this.stuInfo.stuTell;
        this.updateStuInfo.stuPassword = this.updateStuInfo.confirmPassword;
      },
      modifyStuInfo() {
        //表单信息
        var updateStuData = {
          stuUsername: this.updateStuInfo.stuUsername,
          stuTell: this.updateStuInfo.stuTell,
          stuPassword: this.updateStuInfo.stuPassword
        };

        //判断两次密码是否一致
        if (this.updateStuInfo.stuPassword != this.updateStuInfo.confirmPassword) {
          //两次密码不一致 给出错误提示
          this.$message({
            message: "修改失败。提示：两次密码输入不一致喔～",
            type: "failed"
          });
        } else {
          // 提交修改信息 改变post的编码格式，适应后台做修改！
          // todo 传学生姓名、联系方式和密码（updateStuData）给后端
          this.axios
            .post("http://localhost:8004/Students/Edit", qs.stringify(updateStuData))
            .then(result => {
              if (result.data=='success') {
                this.$notify({
                  id: "",
                  title: "修改成功",
                  message: "修改成功！快去看看～",
                  type: "success"
                });
                this.global.role=this.updateStuInfo.stuUsername;
                console.log(this.global.role);
                this.getStuInfo();
              }
            })
            .catch(err => {
              this.$message({
                message: "修改失败o(╥﹏╥)o。" + err + '。',
                type: "danger"
              });
            });

        }

      },


      //查看详细的课程信息
      checkCourseInfo(row) {
        this.courseInfoVisible = true;
        // 数据回显
        this.courseInfo.courseId = row.courseId;
        this.courseInfo.interest = row.interest;
        this.courseInfo.courseCostHour = row.courseCostHour;
        this.courseInfo.courseDescription = row.courseDescription;
        this.courseInfo.courseLocation = row.courseLocation;
        this.courseInfo.date = this.getDateOne(row.courseDate);
        this.courseInfo.time1 = this.getDateTwo(row.courseDate);
        this.courseInfo.time2 = this.getDateThree(row.courseDate);
      },

      //选课表查看选了某节课的学生们的信息
      handleCourses(row) {
        this.stuChooseFormVisible = true;
        this.stuChooseData = []
        row.Enrollments.forEach(item => {
          var newStu = {
            stuUsername: item.stuUsername,
          };
          this.stuChooseData.push(newStu);
        });
      },

      //选课 签到 后端需要修改学生状态
      modifyStatus(row, state) {
        if (row.state == '已签到' && state == '已签到') {
          this.$message({
            message: '这节课已经签到完成啦！',
            type: 'success'
          })
        } else if (state == '待签到') {
          //todo 传上课记录编号attendClassId给后端 -1代表学生从未选过这个课
          // 后端将学生选课记录表attendClassId对应的isCanceledByStu改为0，isAttend改为0
          // this.axios
          //   .post("http://localhost:8004/Students/Edit")
          //   .then(result => {
          //     if (result.data=='success') {
          //     row.state = state;
          //       this.$notify({
          //         message: "选课成功啦！快来看看～",
          //         type: "success"
          //       });
          //     }
          //   })
          //   .catch(err => {
          //     this.$message({
          //       message: "选课失败o(╥﹏╥)o。" + err + '。',
          //       type: "danger"
          //     });
          //   });
          this.$message({
            message: '选课成功！',
            type: 'success'
          })
        } else if (state == '已签到') {
          //todo
          // 后端将学生选课表attendClassId对应的isCanceledByStu改为0，isAttend改为1
          // this.axios
          //   .post("http://localhost:8004/Students/Edit")
          //   .then(result => {
          //     if (result.data=='success') {
          //     row.state = state;
          //       this.$notify({
          //         message: "签到成功～今天上课也要加油喔ヾ(◍°∇°◍)ﾉﾞ",
          //         type: "success"
          //       });
          //     }
          //   })
          //   .catch(err => {
          //     this.$message({
          //       message: "签到失败o(╥﹏╥)o。" + err + '。',
          //       type: "danger"
          //     });
          //   });
          this.$message({
            message: '签到成功！',
            type: 'success'
          })
        }

        row.state = state;

      },

      //查看学生自己的上课记录
      handleCheckCourses() {
        this.dialogStuClassFormVisible = true;
        //todo 向后端获取该学生的上课记录
        // this.axios.get("http://localhost:8004/Courses").then(result => {
        //   if (result.status == 'success) {
        //     this.stuCourseData = result.data
        //   }
        // });
        this.stuCourseData = [
          {
            courseId: '1',
            interest: '篮球课',
            courseDate: '2020-06-13 15:00-17:00',
            courseLocation: '理工体育馆',
            // teaUsername: '金教练'
          },
          {
            courseId: '12',
            interest: '足球课',
            courseDate: '2020-06-12 19:00-21:00',
            courseLocation: '人大体育馆',
            // teaUsername: '林教练'
          }
        ]
      },

      //查询学生个人信息
      getStuInfo() {
        //todo 获取学生个人信息
        // this.axios.get("http://localhost:8004/Courses").then(result => {
        //   if (result.status == 'success') {
        //     this.stuInfo=result.data;
        //   }
        // });
        var result = {
          stuUsername: '我是我啊',
          stuTell: '110120199911',
          stuTotalHour: '99',
          stuRestHour: '12'
        };
        this.stuInfo = result;
        console.log(this.$root.ORDERID);

        this.$root.ORDERID = "xxxxx";
        console.log(this.$root.ORDERID);

      },

      //获取全部已发布的课程信息
      getAllcourses(){
    //todo 获取全部已发布的课程信息
    // this.axios.get("http://localhost:8004/Courses").then(result => {
    //   if (result.status == 200) {
    //     this.coursesData = [],
    //       result.forEach(item => {
    //         var course = {
    //           courseId: item.courseId,
    //           interest: item.interest,
    //           courseCostHour: item.interest,
    //           courseDate: item.interest,
    //           courseLocation: item.interest,
    //           status: item.interest,
    //           state: item.isCanceledByStu === '1' ? '未选课' : item.isAttend === '1' ? '已签到' : '待签到',
    //           isAttend: item.isAttend,
    //           isCanceledByStu: item.isCanceledByStu,
    //           Enrollments: item.Enrollments
    //         };
    //         this.coursesData.push(course);
    //       });
    //
    //     this.loading = false;
    //   }
    // });

        var result =
          [
            {
              attendClassId:'-1',
              courseId: '15767',
              interest: '篮球课',
              courseCostHour: '1',
              courseDate: '2020-06-13 15:00-17:00',
              courseLocation: '理工体育馆',
              status: '0',
              state: '未选课',
              isAttend: '0',
              isCanceledByStu: '0',
              // totalStu: '1',
              Enrollments: [{
                stuId: '11',
                stuUsername: 'www',
                // stuTell: '110'
              }],
              courseDescription: '篮球基本技巧讲解及实践课'
            },
            {
              attendClassId:'1',
              courseId: '123',
              interest: '足球课',
              courseCostHour: '1',
              courseDate: '2020-06-12 19:00-21:00',
              courseLocation: '人大体育馆',
              status: '2',
              state: '已签到',
              // StuNum: '12',
              isAttend: '1',
              isCanceledByStu: '0',
              Enrollments: [{
                stuId: '12',
                stuUsername: 'wy',
                // stuTell: '120'
              },
                {
                  stuId: '3',
                  stuUsername: 'yy',
                  // stuTell: '119'
                }
              ],
              courseDescription: '防守教学'

            },
            {
              attendClassId:'3',
              courseId: '22',
              interest: '体能班',
              courseCostHour: '1',
              courseDate: '2020-06-15 16:00-18:00',
              courseLocation: '北理操场',
              status: '1',
              state: '待签到',
              // StuNum: '7',
              isCanceledByStu: '1',
              Enrollments: [{
                stuId: '12',
                stuUsername: 'wy',
                // stuTell: '120'
              },
                {
                  stuId: '3',
                  stuUsername: 'yy',
                  // stuTell: '119'
                },
                {
                  stuId: '4',
                  stuUsername: 'llly',
                  // stuTell: '188'
                }
              ],
              courseDescription: '防守教学'

            }];
        this.coursesData = [],
          result.forEach(item => {
            var course = {
              attendClassId: item.attendClassId,
              courseId: item.courseId,
              interest: item.interest,
              courseCostHour: item.courseCostHour,
              courseDate: item.courseDate,
              courseLocation: item.courseLocation,
              status: item.interest,
              state: item.isCanceledByStu === '1' ? '未选课' : item.isAttend === '1' ? '已签到' : '待签到',
              isAttend: item.isAttend,
              isCanceledByStu: item.isCanceledByStu,
              Enrollments: item.Enrollments

            };
            this.coursesData.push(course);
          });
        this.loading = false;
      },

    },
    mounted() {
      this.getStuInfo();
      this.getAllcourses();
    }
  };
</script>
<style lang="less" scoped>
  .student {
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

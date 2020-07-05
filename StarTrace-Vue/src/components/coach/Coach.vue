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
          <!-- 查看教练个人信息-->
          <el-timeline-item placement="top">
            <el-card>
              <h4>个人信息</h4>
              <el-form :model="teaInfo" ref="ruleForm" label-width="80px" class="studentForm">
                <el-form-item label="用户名" prop="teaUsername" :label-width="formLabelWidth">
                  <el-input v-model="teaInfo.teaUsername" readonly></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="teaTell" :label-width="formLabelWidth">
                  <el-input v-model="teaInfo.teaTell" readonly></el-input>
                </el-form-item>
                <el-form-item label="已上总课时" prop="teaAllClassHour" :label-width="formLabelWidth">
                  <el-input v-model="teaInfo.teaAllClassHour" readonly></el-input>
                </el-form-item>
                <el-form-item :label-width="formLabelWidth">
                  <el-button type="primary" @click="clickModifyTeaInfo">修改个人信息</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-timeline-item>

          <!-- 发布课程-->
          <el-timeline-item placement="top">
            <el-card>
              <el-link :underline="false" style="padding-left:8em" @click="clickAddCourse">
                <i class="el-icon-view el-icon--right"></i>
                发布课程
                <i class="el-icon-view el-icon--right"></i>
              </el-link>
            </el-card>
          </el-timeline-item>

          <!-- 查看今日课程-->
          <el-timeline-item placement="top">
            <el-card>
              <el-link :underline="false" style="padding-left:8em" @click="checkTodayCourses">
                <i class="el-icon-view el-icon--right"></i>
                今日课程
                <i class="el-icon-view el-icon--right"></i>
              </el-link>
            </el-card>
          </el-timeline-item>

          <!-- 查看上课记录-->
          <el-timeline-item placement="top">
            <el-card>
              <el-link :underline="false" style="padding-left:8em" @click="checkCourses">
                <i class="el-icon-view el-icon--right"></i>
                上课记录
                <i class="el-icon-view el-icon--right"></i>
              </el-link>
            </el-card>
          </el-timeline-item>

        </el-timeline>
      </div>
    </div>

    <!-- 修改个人信息 -->
    <el-dialog title="修改个人信息" :rules="rules" :visible.sync="updateTeaInfoDialogVisible" class="dialogOne">
      <el-form :model="updateTeaInfo" class="content" :rules="rules" ref="updateTeaInfo">
        <el-form-item label="用户名" prop="stuUsername" :label-width="formLabelWidth">
          <el-input v-model="updateTeaInfo.teaUsername" autocomplete="off" required></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="stuTell" :label-width="formLabelWidth">
          <el-input v-model="updateTeaInfo.teaTell" autocomplete="off" required></el-input>
        </el-form-item>
        <el-form-item label="修改密码" prop="teaPassword" :label-width="formLabelWidth">
          <el-input v-model="updateTeaInfo.teaPassword" placeholder="请输入新密码..." autocomplete="off" required></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword" :label-width="formLabelWidth">
          <el-input v-model="updateTeaInfo.confirmPassword" placeholder="请再次输入密码..." autocomplete="off"
                    required></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyTeaInfo">确认修改</el-button>
        <el-button @click="updateTeaInfoDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 发布课程信息 -->
    <el-dialog title="发布课程信息" :rules="rules" :visible.sync="updateCourseInfoDialogVisible" class="dialogOne">
      <el-form :model="addCourseForm" :rules="rules" ref="addCourseForm" label-width="80px" class="coursesForm">
        <el-form-item label="课程类型" prop="interest" required>
          <el-select v-model="addCourseForm.interest" filterable placeholder="请选择课程类型">
            <el-option v-for="item in interest" :key="item.value" :label="item.interest" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课 时 数" prop="courseCostHour">
          <el-input v-model="addCourseForm.courseCostHour" placeholder="请输入课时数"></el-input>
        </el-form-item>
        <el-form-item label="上课日期" required>
          <el-col :span="11">
            <el-form-item prop="date">
              <el-date-picker type="date" placeholder="选择日期" v-model="addCourseForm.date" style="width: 100%;">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="上课时段" required>
          <el-col :span="11">
            <el-form-item prop="time1">
              <el-time-picker placeholder="选择上课时间" v-model="addCourseForm.time1" style="width: 100%;"
                              format="HH:mm" value-format="HH:mm"></el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="11">
            <el-form-item prop="time2">
              <el-time-picker placeholder="选择下课时间" v-model="addCourseForm.time2" style="width: 100%;"
                              format="HH:mm" value-format="HH:mm"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="上课地点" prop="courseLocation">
          <el-input v-model="addCourseForm.courseLocation" placeholder="请输入上课地点"></el-input>
        </el-form-item>
        <el-form-item label="课程简介" prop="courseDescription">
          <el-input
            type="textarea"
            v-model="addCourseForm.courseDescription"
            placeholder="请填写课程信息"
            maxlength="50"
            :autosize="{ minRows: 4, maxRows: 6}"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAddCourse('addCourseForm')">立即添加</el-button>
          <el-button @click="resetForm('addCourseForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

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
<!--        <el-table-column property="courseId" label="课程编号" width="80"></el-table-column>-->
        <el-table-column prop="courseDate" label="上课时间" sortable width="220"></el-table-column>
        <el-table-column prop="courseLocation" label="上课地点" width="150"></el-table-column>
        <el-table-column prop="interest" label="课程类型" width="100"></el-table-column>
        <el-table-column prop="courseCostHour" label="课时数" width="80"></el-table-column>
        <el-table-column label="选课人数" width="120">
          <template slot-scope="scope">
            <el-tooltip content="选课表中查看" placement="top" effect="light" :hide-after="2000">
              <el-button @click="checkChooseStuInfo(scope.row)" type="success" size="small">
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
              <el-button @click="checkCourse(scope.row)" type="warning" size="small">修改</el-button>
            </el-tooltip>
            <el-tooltip content="确定要取消发布该课程吗" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="cancelCourse(scope.$index, scope.row)" type="danger" size="small" icon="el-icon-delete"
                         circle plain></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 查看选课人数具体信息-->
    <el-dialog title="选课学生信息表" :visible.sync="dialogTableVisible" class="dialogOne">
      <el-table :data="stuChooseData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
<!--        <el-table-column property="courseId" label="选课编号" width="100"></el-table-column>-->
        <el-table-column property="stuUsername" label="学生姓名" width="120"></el-table-column>
        <el-table-column property="stuTell" label="联系方式" width="180"></el-table-column>
        <el-table-column property="isAttend" label="是否签到" width="100" class-name="status-col">
          <template slot-scope="{row}">
            <el-tag v-if="row.isAttend=='0'" :type="'danger'">
              待签到
            </el-tag>
            <el-tag v-if="row.isAttend=='1'" :type="'success'">
              已签到
            </el-tag>
          </template>
        </el-table-column>
<!--        <el-table-column label="操作">-->
<!--          <template slot-scope="scope">-->
<!--            <el-tooltip content="删除该学生的选课？" placement="right" effect="light" :hide-after="2000">-->
<!--              <el-button @click="deleteCourse(scope.$index,scope.row)" type="danger" size="small" icon="el-icon-delete"-->
<!--                         circle plain></el-button>-->
<!--            </el-tooltip>-->
<!--          </template>-->
<!--        </el-table-column>-->
      </el-table>
    </el-dialog>

    <!-- 查看和修改课程信息-->
    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" class="dialogOne">
      <el-form :model="updateForm" class="content" :rules="rules" ref="updateForm">
        <el-form-item label="课程编号" prop="courseId"  required>
          <el-input v-model="updateForm.courseId" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="interest" required>
          <el-select v-model="updateForm.interest" filterable placeholder="请选择课程类型">
            <el-option v-for="item in interest" :key="item.value" :label="item.interest"
                       :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所需课时" prop="courseCostHour"  required>
          <el-input v-model="updateForm.courseCostHour" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="上课日期"  required>
          <el-form-item prop="date">
            <el-date-picker type="date" placeholder="选择课程日期" v-model="updateForm.date" style="width: 70%;" readonly></el-date-picker>
          </el-form-item>
        </el-form-item>
        <el-form-item label="上课时段" required>
          <el-col :span="6">
            <el-form-item prop="time1" required>
              <el-time-picker placeholder="选择上课时间" v-model="updateForm.time1" style="width:95%;" format="HH:mm" value-format="HH:mm" ></el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="6">
            <el-form-item prop="time2"  required>
              <el-time-picker placeholder="选择下课时间" v-model="updateForm.time2" style="width:95%;" format="HH:mm" value-format="HH:mm"></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>

<!--        <el-form-item label="上课日期" required>-->
<!--          <el-col :span="11">-->
<!--            <el-form-item prop="date">-->
<!--              <el-date-picker type="date" placeholder="选择日期" v-model="updateForm.date"-->
<!--                              style="width: 100%;"></el-date-picker>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="上课时段" required>-->
<!--          <el-col :span="6">-->
<!--            <el-form-item prop="time1" >-->
<!--              <el-time-picker placeholder="选择上课时间" v-model="updateForm.time1" style="width: 95%;" format="HH:mm" value-format="HH:mm"></el-time-picker>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col class="line" :span="1">-</el-col>-->
<!--          <el-col :span="6">-->
<!--            <el-form-item prop="time2" >-->
<!--              <el-time-picker placeholder="选择下课时间" v-model="updateForm.time2" style="width: 95%;" format="HH:mm" value-format="HH:mm"></el-time-picker>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-form-item>-->
        <el-form-item label="上课地点" prop="courseLocation"  required>
          <el-input v-model="updateForm.courseLocation" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="课程简介" prop="courseDescription" >
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
        <el-button type="primary" @click="modifyCourseInfo('updateForm')">确认修改</el-button>
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 查看今日课程和上课记录 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogTeaClassFormVisible" class="dialogOne">
      <el-table :data="teaCourseData">
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column property="courseDate" label="上课时间" sortable width="200"></el-table-column>
        <el-table-column property="courseLocation" label="上课地点" width="130"></el-table-column><el-table-column label="课程类型" width="100">
          <template slot-scope="scope">
            <el-tooltip content="点击可以查看详细的课程信息喔～" placement="top" effect="light" :hide-after="1000" :enterable="false">
              <el-button @click="checkCourseInfo(scope.row)" type="primary" size="small">
                <strong>{{scope.row.interest}}</strong>
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!--      查看课程信息-->
    <el-dialog title="课程信息" :visible.sync="courseInfoVisible" class="dialogOne">
      <el-form :model="courseInfo"  class="content" ref="updateForm">
        <el-form-item label="课程编号" prop="courseId" :label-width="formLabelWidth" required>
          <el-input v-model="courseInfo.courseId" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="课程类型" prop="interest" :label-width="formLabelWidth" required>
          <el-input v-model="courseInfo.interest" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="课时数" prop="courseCostHour" :label-width="formLabelWidth" required>
          <el-input v-model="courseInfo.courseCostHour" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="上课日期" :label-width="formLabelWidth" required>
          <el-form-item prop="date">
            <el-date-picker type="date" placeholder="选择课程日期" v-model="courseInfo.date" style="width: 70%;" readonly></el-date-picker>
          </el-form-item>
        </el-form-item>
        <el-form-item label="上课时段" :label-width="formLabelWidth" required>
          <el-col :span="11">
            <el-form-item prop="time1" required>
              <el-time-picker placeholder="选择上课时间" v-model="courseInfo.time1" style="width:70%;" format="HH:mm" value-format="HH:mm" readonly></el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="11">
            <el-form-item prop="time2"  required>
              <el-time-picker placeholder="选择下课时间" v-model="courseInfo.time2" style="width:70%;" format="HH:mm" value-format="HH:mm" readonly></el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>

        <el-form-item label="上课地点" prop="courseLocation" :label-width="formLabelWidth" required>
          <el-input v-model="courseInfo.courseLocation" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="课程简介" prop="courseDescription" :label-width="formLabelWidth" required>
          <el-input type="textarea" v-model="courseInfo.courseDescription" placeholder="请填写课程信息" maxlength="50"
                    :autosize="{ minRows: 4, maxRows: 6}" show-word-limit readonly></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="courseInfoVisible = false">确 定</el-button>
      </div>
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
          {interest: "体能课", value: 2},
          {interest: "轮滑课", value: 3},
        ],

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
          confirmPassword: [
            {required: true, message: "请输入密码", trigger: "change"},
            {min: 6, max: 15, message: "两次密码需保持一致，长度为6到15个字符", trigger: "blur"}
          ],
          teaTell: [
            {required: true, message: "请输入手机号码", trigger: "change"},
            {min: 11, max: 11, message: "再检查一下手机号码有没有输入错误喔～", trigger: "blur"}
          ],
          interest: [{required: true, message: "请选择具体的课程类型", trigger: "blur"}],
          courseCostHour: [{required: true, message: "请输入具体的课时数", trigger: "blur"}],
          date: [{required: true, message: "请选择具体的日期", trigger: "blur"}],
          time1: [{required: true, message: "请选择课程开始时间", trigger: "blur"}],
          time2: [{required: true, message: "请选择课程结束时间", trigger: "blur"}],
          courseLocation: [{required: true, message: "请输入具体的上课地点", trigger: "blur"}],
          courseDescription: [{required: true, message: "请填写课程信息,否则无法通过喔~", trigger: "change"}]
        },

        // 教练个人信息 表单项设置
        teaInfo: {
          teaUsername: '',
          teaTell: '',
          interest: '',
          teaAllClassHour: '',
        },

        // 修改教练个人信息信息
        updateTeaInfoDialogVisible: false,
        updateTeaInfo: {
          teaUsername: '',
          teaTell: '',
          interest: '',
          teaAllClassHour: '',
          teaPassword: '',
          confirmPassword: '',
        },

        // 添加课程 表单项设置
        addCourseForm: {
          interest: "",
          courseCostHour: "",
          date: "",
          time1: "",
          time2: "",
          courseLocation: "",
          courseDescription: ""
        },
        //发布课程
        updateCourseInfoDialogVisible:false,

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
        //修改课程信息
        dialogFormVisible: false,
        formLabelWidth: "6em",

        //选了某节课的学生的信息
        stuChooseData: [],
        dialogTableVisible: false,

        //今日课程&上课记录
        dialogTitle:'',
        teaCourseData: [],
        dialogTeaClassFormVisible: false,
        dialogFutureClassFormVisible: false,


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
        courseInfoVisible: false,
        formLabelWidth: "7em",

        //最近发布的课程信息 总表
        coursesData: [],

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
      //日期转换 获得年月日"YYYY-MM-DD"
      getDateOne(time) {
        var timeArr = time.split(" ");
        var d = timeArr[0].split("-");
        return new Date(d[0], d[1] - 1, d[2]);
      },
      //获得上课时间 "HH:MM"
      getDateTwo(time) {
        var timeArr = time.split(" ");
        var t = timeArr[1].split("-");
        var h = t[0].split(":");
        return h[0] + ":" + h[1];
      },
      //获得下课时间 "HH:MM"
      getDateThree(time) {
        var timeArr = time.split(" ");
        var t = timeArr[1].split("-");
        var h = t[1].split(":");
        return h[0] + ":" + h[1];

      },
      // 将新添加课程的上课时间进行格式转换（注：data是日期 date2是时间）格式"YYYY-MM-DD HH:MM:SS"
      sendAddTime(date, date2) {
        var Y = date.getFullYear();
        var M =
          date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1;
        var MT = parseInt(M) + 1;
        var D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var addTime = Y + "-" + M + "-" + D + " " + date2 + ":00";
        return addTime;
      },

      //查询教练个人信息
      queryTeaInfo() {
        //todo 获取教练个人信息
        this.axios.post("http://112.124.29.52:8081/tea/queryMyInfo").then(result => {
          if (result) {
            this.teaInfo = result.data;}
        });
      },
      // 修改教练个人信息
      clickModifyTeaInfo() {
        this.updateTeaInfoDialogVisible = true;
        //回显
        this.updateTeaInfo.teaUsername = this.teaInfo.teaUsername;
        this.updateTeaInfo.teaTell = this.teaInfo.teaTell;
        this.updateTeaInfo.interest = this.teaInfo.interest;
        this.updateTeaInfo.teaPassword = this.updateTeaInfo.confirmPassword;
      },
      modifyTeaInfo() {
        //表单信息
        var updateTeaData = {
          teaUsername: this.updateTeaInfo.teaUsername,
          teaTell: this.updateTeaInfo.teaTell,
          interest: this.updateTeaInfo.interest,
          teaPassword: this.updateTeaInfo.teaPassword
        };

        //判断两次密码是否一致
        if (this.updateTeaInfo.teaPassword != this.updateTeaInfo.confirmPassword) {
          //两次密码不一致 给出错误提示
          this.$message({
            message: "修改失败。提示：两次密码输入不一致喔～",
            type: "failed"
          });
        } else {
          // 提交修改信息 改变post的编码格式，适应后台做修改！
          // todo 传教练姓名、联系方式和密码（updateTeaData）给后端
          // 基本已经完成
          this.axios
            .post("http://112.124.29.52:8081/tea/updateInformation", qs.stringify(updateTeaData))
            .then(result => {
              this.updateTeaInfoDialogVisible = false;
              if (result.data == 'success') {
                this.$notify({
                  id: "",
                  title: "修改成功",
                  message: "修改成功！快去看看～",
                  type: "success"
                });
                this.queryTeaInfo();
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

      // 发布课程
      clickAddCourse(){
        this.updateCourseInfoDialogVisible=true;
      },
      submitAddCourse(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            // 表单验证成功
            var course = {
              interest: this.interest[this.addCourseForm.interest].interest,
              courseCostHour: this.addCourseForm.courseCostHour,
              courseStartDate: this.sendAddTime(
                this.addCourseForm.date,
                this.addCourseForm.time1
              ),
              courseEndDate: this.sendAddTime(
                this.addCourseForm.date,
                this.addCourseForm.time2
              ),
              courseLocation: this.addCourseForm.courseLocation,
              courseDescription: this.addCourseForm.courseDescription
            };
            // 改变post的编码格式，适应后台
            this.axios
              .post("http://112.124.29.52:8081/tea/addCourse", qs.stringify(course))
              .then(result => {
                this.updateCourseInfoDialogVisible=false;
                if (result.data == 'success') {
                  this.resetForm('addCourseForm');
                  this.$message({
                    message: "添加成功，快去看看吧(*￣︶￣)",
                    type: "success"
                  });
                  this.queryAllcourses();
                }
                else if(result.data=='failure')
                {
                  this.$message({
                    message: "不能发布今天之前的课程噢",
                    type: "danger"
                  });
                }
              })
              .catch(err => {
                this.resetForm('addCourseForm');
                this.$message({
                  message: "添加失败o(╥﹏╥)o," + err + '。',
                  type: "danger"
                });
              });
          } else {
            this.$message.error("填写不完整喔！我快马加鞭前来提示！");
            return false;
          }
        });
      },

      // 获取自己全部已发布的课程信息
      queryAllcourses() {
        this.axios.post("http://112.124.29.52:8081/tea/queryMyCourse").then(result => {
          if (result) {
            this.coursesData = result.data;
            this.loading = false;
            // console.log(result);
          }
        });
      },

      //查看课程详细信息
      checkCourse(row) {
        this.dialogFormVisible = true;
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

      },

      // 修改课程信息
      modifyCourseInfo(formName) {
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
              .post("http://112.124.29.52:8081/tea/updateCourseInfo", qs.stringify(updateData))
              .then(result => {
                if (result.data == 'success') {
                  this.$notify({
                    id: "",
                    title: "修改成功",
                    message: "修改成功！请注意查看。",
                    type: "success"
                  });
                  this.queryAllcourses();
                }
              })
              .catch(err => {
                this.$message({
                  message: "修改失败o(╥﹏╥)o。" + err + '。',
                  type: "danger"
                });
              });
          } else {
            this.$message.error("阁下网络错误！刘大侠我快马加鞭前来提示！");
            return false;
          }
        });
      },

      // 取消发布 删除课程
      cancelCourse(index, row) {
        this.$confirm("此操作将删除该课程所有信息, 是否继续?", "提示", {
          cancelButtonText: "取消",
          confirmButtonText: "确定",
          type: "warning"
        })
          .then(() => {
            //TODO 老师取消课程后端要把该课程的isCanceledByTea修改为1
            this.axios
              .post("http://112.124.29.52:8081/tea/cancelCourse", qs.stringify({'courseId': row.courseId}))
              .then(result => {
                if (result.data == 'success') {
                  this.$message({
                    message: "删除成功(*￣︶￣)，",
                    type: "success"
                  });
                  this.queryAllcourses();
                }
                else if(result.data=='failure'){
                  // this.queryAllcourses();
                  this.$message({
                    message: "不能将已经结束的课程取消发布喔～",
                    type: "danger"
                  });
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
      checkChooseStuInfo(row) {
        this.dialogTableVisible = true;
        this.stuChooseData = [];
        row.Enrollments.forEach(item => {
          var newCourse = {
            // courseId: row.courseId,
            courseId : item.attendClassId,
            stuUsername: item.stuUsername,
            stuTell: item.stuTell,
            isAttend: item.isAttend,
          };
          this.stuChooseData.push(newCourse);
        });
      },

      //教练今日课程
      checkTodayCourses(){
        this.dialogTitle='即将要上的课程';
        this.dialogTeaClassFormVisible = true;
        this.axios.post("http://112.124.29.52:8081/tea/queryCourseInFuture").then(result => {
          if (result) {
            this.teaCourseData = result.data;
          }
        });
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

      //查看自己的上课记录
      checkCourses() {
        this.dialogTitle='上课记录';
        this.dialogTeaClassFormVisible = true;
        // todo 向后端获取这个教练员的上课记录信息
        this.axios.post("http://112.124.29.52:8081/tea/queryCourseInPast").then(result => {
          if (result) {
            this.teaCourseData =result.data;
            // console.log(result);
          }
        });
      },

      goBack() {
        this.$root.levelid =3;
        sessionStorage.clear();
        window.history.back();
      },

    },
    mounted() {
      this.queryTeaInfo();
      this.queryAllcourses();
      //监听浏览器返回键
      if (window.history && window.history.pushState) {
        // 向历史记录中插入了当前页
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', this.goBack, false);
      }
      this.$root.levelid=1;
    },
    destroyed() {
      window.removeEventListener('popstate', this.goBack, false);
    },
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

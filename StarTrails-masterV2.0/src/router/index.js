import Vue from 'vue'
import Router from 'vue-router'
import Courses from "@/components/courses/Courses.vue"
import Students from "@/components/students/Students"
import Enrollments from "@/components/enrollments/Enrollments"
import Aboutme from "@/components/Aboutme"
import Register from "@/components/enrollments/Register"
import Admin from "@/components/admin/Admin"
import Coach from "@/components/coach/Coach"


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      // name: 'HelloWorld',
      redirect:"/enrollments"
    },
    {
      path:"/students",
      name:"students",
      component:Students,
      meta:{
        role:0
      }
    },
    {
      path:"/courses",
      name:"courses",
      component:Courses
    },
    {
      path:"/enrollments",
      name:"enrollments",
      component:Enrollments
    },
    {
      path:"/aboutme",
      name:"aboutme",
      component:Aboutme
    },
    {
      path:"/register",
      name:"register",
      component:Register
    },
    {
      path:"/admin",
      name:"admin",
      component:Admin
    },
    {
      path:"/coach",
      name:"coach",
      component:Coach
    }

  ]
})

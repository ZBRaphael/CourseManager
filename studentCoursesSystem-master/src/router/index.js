import Vue from 'vue'
import Router from 'vue-router'
import Coach from "@/components/coach/Coach.vue"
import Students from "@/components/students/Students"
import Enrollments from "@/components/enrollments/Enrollments"
import Aboutme from "@/components/Aboutme"
import Admin from "@/components/admin/Admin.vue"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      redirect:"/Enrollments"
    },
    {
      path:"/students",
      name:"students",
      component:Students
    },
    {
      path:"/coach",
      name:"coach",
      component:Coach
    },
    {
      path:"/admin",
      name:"admin",
      component:Admin
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
    }

  ]
})

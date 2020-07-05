import Vue from 'vue'
import Router from 'vue-router'
import Students from "@/components/students/Students"
import Index from "@/components/index/Login"
import Register from "@/components/index/Register"
import Admin from "@/components/admin/Admin"
import Coach from "@/components/coach/Coach"


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      // name: 'HelloWorld',
      redirect:"/login"
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
      path:"/login",
      name:"login",
      component:Index
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

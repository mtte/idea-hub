import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'
import UserOverview from '../views/user/Overview'
import NoteCrud from '../views/note/Crud'
import NotFound from '../views/404'
import Forbidden from '../views/403'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/userOverview',
    name: 'userOverview',
    component: UserOverview
  },
  {
    path: '/note/:id',
    name: 'noteCrud',
    component: NoteCrud
  },
  {
    path: '/403',
    name: 'Forbidden',
    component: Forbidden
  },
  {
    path: '*',
    name: 'NotFound',
    component: NotFound
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['login', 'NotFound', 'Forbidden']
  const authorPages = ['noteCrud']
  const adminPages = ['userOverview']
  const user = JSON.parse(localStorage.getItem('user'))
  const isLoggedIn = user !== null

  const authRequired = !publicPages.includes(to.name)
  const authorRequired = authorPages.includes(to.name)
  const adminRequired = adminPages.includes(to.name)

  if (authRequired && !isLoggedIn) {
    next('/login')
    return
  }

  if (authRequired && isLoggedIn) {
    const isAuthor = user.role === 'AUTHOR'
    const isAdmin = user.role === 'ADMIN'

    if (authorRequired && !(isAuthor || isAdmin)) {
      return next('/403')
    }

    if (adminRequired && !isAdmin) {
      return next('/403')
    }
  }

  next()
})

export default router

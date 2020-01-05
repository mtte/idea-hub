import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'
import UserOverview from '../views/user/Overview'

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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['login']
  const authorPages = ['']
  const adminPages = ['userOverview']
  const user = JSON.parse(localStorage.getItem('user'))
  const isLoggedIn = user !== null

  const authRequired = !publicPages.includes(to.name)
  const authorRequired = authorPages.includes(to.name)
  const adminRequired = adminPages.includes(to.name)

  // eslint-disable-next-line no-debugger
  // debugger

  if (authRequired && !isLoggedIn) {
    next('/login')
    return
  }

  if (authRequired && isLoggedIn) {
    const isAuthor = user.role === 'AUTHOR'
    const isAdmin = user.role === 'ADMIN'

    if (authorRequired && (!isAuthor || !isAdmin)) {
      return next(from)
    }

    if (adminRequired && !isAdmin) {
      return next(from)
    }
  }

  next()
})

export default router

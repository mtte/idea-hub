import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)

Vue.axios.interceptors.request.use(function (request) {
  const user = JSON.parse(localStorage.getItem('user'))
  if (user && user.token) {
    request.headers['Authorization'] = 'Bearer ' + user.token
    request.withCredentials = true
  }
  return request
})
Vue.axios.defaults.baseURL = 'http://localhost:4567/api'
Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

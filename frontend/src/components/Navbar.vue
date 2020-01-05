<template>
  <v-app-bar
    app
    color="deep-purple darken-4"
    dark
  >
    <router-link
      class="d-flex align-center headline"
      to="/"
      style="text-decoration: none; color: white;"
    >
      <span>IDEA</span><span class="font-weight-black">HUB</span>
    </router-link>

    <v-spacer />

    <v-btn
      v-if="role === 'ADMIN'"
      icon
      large
      style="margin-right: 70px"
      to="/userOverview"
    >
      <v-icon>mdi-account-supervisor</v-icon>
    </v-btn>

    <v-btn
      v-if="role === 'ADMIN' || role === 'AUTHOR'"
      fab
      color="amber accent-4"
      bottom
      right
      absolute
      to="/note/new"
    >
      <v-icon color="deep-purple darken-4">
        mdi-plus
      </v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
export default {
  name: 'Navbar',
  data () {
    return {
      role: ''
    }
  },
  watch: {
    '$route' () {
      this.updateRole()
    }
  },
  created () {
    this.updateRole()
  },
  methods: {
    updateRole () {
      const user = JSON.parse(localStorage.getItem('user'))
      if (user) {
        this.role = user.role
      } else {
        this.role = ''
      }
    }
  }
}
</script>

<style scoped>

</style>

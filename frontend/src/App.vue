<template>
  <v-app>
    <Navbar />

    <v-content>
      <v-container
        fluid
      >
        <router-view />
      </v-container>
    </v-content>
  </v-app>
</template>

<script>

import Navbar from './components/Navbar'
export default {
  name: 'App',
  components: { Navbar },
  data () {
    return {
    }
  }

}
</script>

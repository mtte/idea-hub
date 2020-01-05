<template>
  <v-app>
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
        icon
        large
        style="margin-right: 70px"
        to="userOverview"
      >
        <v-icon>mdi-account-supervisor</v-icon>
      </v-btn>

      <v-btn
        fab
        color="amber accent-4"
        bottom
        right
        absolute
      >
        <v-icon color="deep-purple darken-4">
          mdi-plus
        </v-icon>
      </v-btn>
    </v-app-bar>

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

export default {
  name: 'App',

  data: () => ({
    //
  })
}
</script>

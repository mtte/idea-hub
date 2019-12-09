<template>
  <v-app>
    <v-app-bar
      app
      color="deep-purple darken-4"
      dark
    >
      <div class="d-flex align-center">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
          transition="scale-transition"
          width="40"
        />

        <v-img
          alt="Vuetify Name"
          class="shrink mt-1 hidden-sm-and-down"
          contain
          min-width="100"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
          width="100"
        />
      </div>

      <v-spacer />

      <v-btn
        icon
        large
        style="margin-right: 70px"
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

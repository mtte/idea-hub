<template>
  <v-container>
    <h1>Benutzer</h1>
    <v-form
      ref="form"
      @subit.prevent="save"
    >
      <v-text-field
        v-model="user.username"
        label="Benutzername"
        required
        :counter="50"
        :rules="usernameRules"
      />
      <v-text-field
        v-if="mode === 'new'"
        v-model="user.password"
        label="Passwort"
        required
        :counter="50"
        :rules="passwordRules"
      />
      <v-select
        v-model="user.role"
        label="Rolle"
        :items="roles"
        required
        :rules="[v => !!v || 'Es muss eine Rolle ausgewählt werden']"
      />
    </v-form>
    <v-btn
      color="amber accent-4"
      fab
      large
      bottom
      right
      @click="save"
    >
      <v-icon color="white">
        mdi-content-save
      </v-icon>
    </v-btn>
    <v-alert
      v-if="error"
      type="error"
      style="margin-top: 10px"
    >
      {{ error }}
    </v-alert>
    <v-snackbar
      v-model="snackbar"
      :timeout="2000"
      color="success"
    >
      {{ snackbarText }}
    </v-snackbar>
  </v-container>
</template>

<script>
export default {
  name: 'UserCrud',
  data () {
    return {
      user: {},
      mode: '',
      error: '',
      snackbar: false,
      snackbarText: '',
      roles: [
        'USER', 'AUTHOR', 'ADMIN'
      ],
      usernameRules: [
        v => !!v || 'Benutzername ist ein Pflichtfeld',
        v => (v && v.length <= 50) || 'Benutzername darf maximal 50 Zeichen lang sein'
      ],
      passwordRules: [
        v => !!v || 'Passwort ist ein Pflichtfeld',
        v => (v && v.length >= 12) || 'Passwort muss mindestens 12 Zeichen lang sein',
        v => (v && v.length <= 50) || 'Passwort darf maximal 50 Zeichen lang sein'
      ]
    }
  },
  mounted () {
    const id = this.$route.params.id
    if (id === 'new') {
      this.mode = 'new'
      this.user = {}
    } else {
      this.mode = 'edit'
      this.axios.get(`/users/${id}`)
        .then(response => {
          this.user = response.data
        })
        .catch(() => {
          this.error = 'User konnte nicht gefunden werden'
          this.user = ''
        })
    }
  },
  methods: {
    save () {
      if (this.$refs.form.validate()) {
        if (this.mode === 'new') {
          const payload = {
            'username': this.user.username,
            'password': this.user.password,
            'role': this.user.role
          }
          this.axios.post('/users', payload).then(response => {
            this.error = ''
            this.snackbarText = 'Erfolgreich erstelllt'
            this.snackbar = true
          })
            .catch(error => {
              if (error.response.status === 400) {
                this.error = 'Passwort muss mind 3 von folgenden 4 Kriterien erfüllen (Klein-, Grossbuchstaben, Zahlen, Sonderzeichen)'
              } else {
                this.error = 'Es ist etwas schiefgelaufen'
              }
            })
        }
        if (this.mode === 'edit') {
          const payload = {
            'username': this.user.username,
            'role': this.user.role
          }
          this.axios.put(`/users/${this.user.id}`, payload)
            .then(response => {
              this.error = ''
              this.snackbarText = 'Erfolgreich erstelllt'
              this.snackbar = true
              this.user = response.data
            })
            .catch(() => {
              this.error = 'Es ist etwas schiefgelaufen'
            })
        }
      }
    }
  }
}
</script>

<style scoped>

</style>

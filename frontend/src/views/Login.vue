<template>
  <v-container>
    <h1>Login</h1>

    <form @submit.prevent="login">
      <v-text-field
        v-model="username"
        :error-messages="usernameErrors"
        label="Benutzername"
        required
        @input="$v.username.$touch()"
        @blur="$v.username.$touch()"
      />
      <v-text-field
        v-model="password"
        :error-messages="passwordErrors"
        label="Passwort"
        required
        type="password"
        @input="$v.password.$touch()"
        @blur="$v.password.$touch()"
      />

      <v-text-field
        v-model="twoFA"
        label="2FA"
      />

      <v-btn
        class="mr-4"
        :loading="loading"
        @click="login"
      >
        Login
      </v-btn>
    </form>
    <v-alert
      v-if="error"
      type="error"
      style="margin-top: 10px"
    >
      {{ error }}
    </v-alert>
  </v-container>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
  mixins: [validationMixin],

  validations: {
    username: { required },
    password: { required }
  },

  data () {
    return {
      username: 'admin',
      password: 'pass4dev',
      twoFA: '',

      loading: false,
      error: null
    }
  },

  computed: {
    usernameErrors () {
      const errors = []
      if (!this.$v.username.$dirty) return errors
      !this.$v.username.required && errors.push('Benutzername ist ein Pflichtfeld.')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.password.$dirty) return errors
      !this.$v.password.required && errors.push('Passwort ist ein Pflichtfeld.')
      return errors
    }
  },

  methods: {
    login () {
      this.loading = true
      this.$v.$touch()
      if (!this.$v.$invalid) {
        const payload = {
          username: this.username,
          password: this.password,
          '2fa': this.twoFA
        }
        this.axios.post('/login', payload)
          .then(response => {
            localStorage.setItem('user', JSON.stringify(response.data))
            this.error = ''
            this.$router.push('/')
          })
          .catch(error => {
            switch (error.response.status) {
              case 401:
                this.error = 'Benutzername, Passwort oder 2FA falsch'
                break
              default:
                this.error = 'Es ist ein Fehler aufgetreten. Bitte versuchen sie es später nocheinmal.'
            }
          })
          .finally(() => {
            this.loading = false
          })
      }
    }
  }
}
</script>

<template>
  <v-container>
    <p>Zwei-Faktor-Authentisierung aktiv: {{ twoFA.enabled }}</p>
    <v-img
      v-if="twoFA.enabled && twoFA.url"
      :src="twoFA.url"
      style="max-width: 300px"
    />
    <v-btn
      v-if="!twoFA.enabled"
      :disabled="twoFA.enabled"
      @click="enable"
    >
      Aktivieren
    </v-btn>
    <v-btn
      v-if="twoFA.enabled"
      :disabled="!twoFA.enabled"
      @click="disable"
    >
      Deaktivieren
    </v-btn>
    <v-snackbar
      v-model="snackbar"
      :timeout="2000"
      color="success"
    >
      Erfolg
    </v-snackbar>
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
export default {
  name: 'TwoFA',
  data () {
    return {
      twoFA: {
        enabled: true,
        url: ''
      },
      error: '',
      snackbar: false
    }
  },
  mounted () {
    this.getStatus()
  },
  methods: {
    getStatus () {
      this.axios.get('/2fa')
        .then(response => {
          this.error = ''
          this.twoFA.enabled = response.data
        })
        .catch(() => {
          this.error = 'Es ist etwas schiefgelaufen'
        })
    },
    enable () {
      this.axios.post('/2fa')
        .then(response => {
          this.snackbar = true
          this.error = ''
          this.twoFA.url = response.data
        })
        .catch(() => {
          this.error = 'Es ist etwas schiefgelaufen'
        })
        .finally(() => {
          this.getStatus()
        })
    },
    disable () {
      this.axios.delete('/2fa')
        .then(response => {
          this.snackbar = true
          this.error = ''
          console.log(response.data)
        })
        .catch(() => {
          this.error = 'Es ist etwas schiefgelaufen'
        })
        .finally(() => {
          this.getStatus()
        })
    }
  }
}
</script>

<style scoped>

</style>

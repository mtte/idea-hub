<template>
  <v-container>
    <h1>Notiz {{ mode }}</h1>
    <v-form
      v-if="note"
      ref="form"
      @submit.prevent="save"
    >
      <v-text-field
        v-model="note.title"
        label="Titel"
        required
        :counter="100"
        :rules="titleRules"
      />
      <v-switch
        v-model="note.isShared"
        label="Teilen"
      />
      <v-row id="editor">
        <v-col cols="6">
          <v-textarea
            v-model="note.content"
            :rules="[v => !!v || 'Pflichtfeld']"
            label="Inhalt (Markdown)"
          />
        </v-col>
        <v-col cols="6">
          <vue-markdown :source="note.content" />
        </v-col>
      </v-row>
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
import VueMarkdown from 'vue-markdown'

export default {
  name: 'NoteCrud',
  components: {
    VueMarkdown
  },
  data () {
    return {
      note: {},
      mode: '',
      error: '',
      snackbar: false,
      snackbarText: '',
      titleRules: [
        v => !!v || 'Titel ist ein Pflichtfeld',
        v => (v && v.length >= 5) || 'Titel muss mindestens 5 Zeichen lang sein',
        v => (v && v.length <= 100) || 'Titel darf maximal 100 Zeichen lang sein'
      ]
    }
  },
  mounted () {
    const id = this.$route.params.id
    if (id === 'new') {
      this.mode = 'new'
      this.note = {}
      this.note.isShared = false
    } else {
      this.mode = 'edit'
      this.axios.get(`/notes/${id}`)
        .then(response => {
          this.note = response.data
        })
        .catch(() => {
          this.error = 'Notiz konnte nicht gefunden werden'
          this.note = ''
        })
    }
  },
  methods: {
    save () {
      if (this.$refs.form.validate()) {
        if (this.mode === 'new') {
          const payload = {
            'title': this.note.title,
            'content': this.note.content,
            'shared': this.note.isShared
          }
          this.axios.post('/notes', payload)
            .then(response => {
              this.snackbarText = 'Erfolgreich erstelllt'
              this.snackbar = true
              this.note = {}
              this.note.isShared = false
              this.error = ''
            })
            .catch(() => {
              this.error = 'Es ist etwas schiefgelaufen'
            })
        }
        if (this.mode === 'edit') {
          const payload = {
            'title': this.note.title,
            'content': this.note.content,
            'shared': this.note.isShared
          }
          this.axios.put(`/notes/${this.note.id}`, payload)
            .then(response => {
              this.error = ''
              this.snackbarText = 'Erfolgreich gespeichert'
              this.snackbar = true
              this.note = response.data
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

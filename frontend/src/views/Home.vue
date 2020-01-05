<template>
  <v-container>
    <v-row dense>
      <v-col
        v-for="note in notes"
        :key="note.id"
        cols="12"
      >
        <NoteCard :note="note" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import NoteCard from '../components/NoteCard'
export default {
  name: 'Home',
  components: { NoteCard },

  data () {
    return {
      notes: []
    }
  },

  beforeMount () {
    this.axios.get('/notes').then(response => {
      this.notes = response.data
    })
  }
}
</script>

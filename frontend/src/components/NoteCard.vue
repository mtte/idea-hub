<template>
  <v-card
    light
  >
    <v-card-title class="headline">
      {{ note.title }}
    </v-card-title>

    <v-card-subtitle>
      {{ note.author }}
    </v-card-subtitle>

    <v-card-text>
      <vue-markdown
        :source="note.content"
      />
    </v-card-text>
  </v-card>
</template>

<script>
import VueMarkdown from 'vue-markdown'

export default {
  name: 'NoteCard',
  components: {
    VueMarkdown
  },
  props: {
    note: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>

</style>

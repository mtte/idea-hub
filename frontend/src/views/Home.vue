<template>
  <v-container>
    <v-row dense>
      <v-col cols="12">
        <NoteCard />
      </v-col>
      <v-col cols="12">
        <NoteCard />
      </v-col>
      <v-col cols="12">
        <NoteCard />
      </v-col>
      <v-col cols="12">
        <NoteCard />
      </v-col>
      <v-col cols="12">
        <NoteCard />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import NoteCard from '../components/NoteCard'
export default {
  name: 'Home',
  components: { NoteCard }
}
</script>

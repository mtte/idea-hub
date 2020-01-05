<template>
  <div>
    <v-card-title>
      Benutzer
      <v-spacer />
      <v-text-field
        v-model="search"
        label="Suchen"
        single-line
        hide-details
      />
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
    />
  </div>
</template>
<script>
export default {
  name: 'UserOverview',
  data () {
    return {
      search: '',
      headers: [
        { text: 'ID', value: 'id' },
        { text: 'Benutzername', value: 'username' },
        { text: 'Rolle', value: 'role' }
      ],
      users: []
    }
  },
  beforeMount () {
    this.axios.get('/users').then(response => {
      this.users = response.data
    })
  }
}
</script>

<style scoped>

</style>

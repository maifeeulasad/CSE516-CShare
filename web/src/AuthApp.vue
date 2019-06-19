<template>
  <div id="app">
    <v-app>
      <v-content id="content">
        <router-view/>
      </v-content>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'AuthApp'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

#content {
  margin: 18px;
  padding: 8px;
}
</style>

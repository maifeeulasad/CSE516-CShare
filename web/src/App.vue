<template>
  <div id="app">
    <v-app>
      <Navbar />
      <v-content id="content">
        <router-view/>
      </v-content>
    </v-app>
  </div>
</template>

<script>
import Navbar from '@/components/_parts/Navbar'
export default {
  name: 'App',
  components: { Navbar }
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

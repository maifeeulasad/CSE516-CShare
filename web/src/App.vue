<template>
  <div id="app">
    <v-app>
      <Navbar :userFullName = "userFullName" :userProfileImageUrl = "userProfileImageUrl" />
      <v-content id="content">
        <router-view/>
      </v-content>
    </v-app>
  </div>
</template>

<script>
import Navbar from '@/components/_parts/Navbar'
import firebase from 'firebase'
export default {
  name: 'App',
  components: { Navbar },
  data () {
    return {
      userFullName: '',
      userProfileImageUrl: ''
    }
  },
  mounted () {
    let username = ''
    let userProfilePhotoUrl = ''
    firebase.database().ref('/users/' + firebase.auth().currentUser.uid).once('value').then(function (snapshot) {
      username = (snapshot.val() && snapshot.val().Name) || 'Anonymous'
      userProfilePhotoUrl = (snapshot.val() && snapshot.val().ProfilePhotoUrl) || 'https://randomuser.me/api/portraits/men/7.jpg'
    })
    this.userFullName = username
    this.userProfileImageUrl = userProfilePhotoUrl
  }
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

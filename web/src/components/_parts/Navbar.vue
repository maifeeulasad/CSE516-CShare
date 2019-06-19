<template>
  <nav>
    <v-toolbar app class="white">

      <v-spacer></v-spacer>

      <v-menu :nudge-width="150" offset-y bottom left>
        <template v-slot:activator="{ on }">
          <v-btn icon v-on="on">
            <v-icon>fas fa-ellipsis-v</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-tile @click="logout">
            <v-list-tile-title>Logout</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-menu>

    </v-toolbar>
    <v-navigation-drawer app permanent absolute class="white">
      <v-toolbar class="white">
        <v-toolbar-title>CShare</v-toolbar-title>
      </v-toolbar>
      <v-toolbar flat class="transparent">
        <v-list class="pa-0">
          <v-list-tile avatar>
            <v-list-tile-avatar>
              <img :src="userProfileImageUrl">
            </v-list-tile-avatar>

            <v-list-tile-content>
              <v-list-tile-title>{{ userFullName }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list>
      </v-toolbar>

      <v-list class="pt-0" dense>
        <v-divider></v-divider>

        <v-list-tile
          v-for="item in items"
          :key="item.title"
          :to="item.action"
        >
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>

          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import firebase from 'firebase'
export default {
  name: 'Navbar',
  props: ['userFullName', 'userProfileImageUrl'],
  data () {
    return {
      items: [
        { title: 'Dashboard', icon: 'fas fa-tachometer-alt', action: '/dashboard' },
        { title: 'Users', icon: 'fas fa-users', action: '/users' },
        { title: 'Files', icon: 'fas fa-folder-open', action: '/files' },
        { title: 'Reports', icon: 'fas fa-bug', action: '/reports' },
        { title: 'Profile', icon: 'fas fa-user', action: '/profile' },
        { title: 'Sign out', icon: 'fas fa-sign-out-alt', action: '/logout' }
      ]
    }
  },
  methods: {
    logout: function () {
      firebase.auth().signOut().then(function () {
        location.reload()
      }).catch(function (error) {
        console.log(error.message)
      })
    }
  }
}
</script>

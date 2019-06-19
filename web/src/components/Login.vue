<template>
  <v-app id="inspire">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-form method="post">
              <v-card class="elevation-12">
                <v-toolbar dark color="primary">
                  <v-toolbar-title>CShare - Login</v-toolbar-title>
                </v-toolbar>
                <v-card-text>
                    <p v-if="errors.length" class="red--text">
                      <b>Please correct the following error(s):</b>
                      <ul>
                        <li v-for="error in errors" :key="error">{{ error }}</li>
                      </ul>
                    </p>
                    <v-text-field v-model="email" id="email" prepend-icon="fas fa-envelope" name="email" label="Email" type="email"></v-text-field>
                    <v-text-field v-model="password" id="password" prepend-icon="fas fa-lock" name="password" label="Password" type="password"></v-text-field>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" @click="login">Login</v-btn>
                </v-card-actions>
              </v-card>
            </v-form>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import firebase from 'firebase'
export default {
  name: 'Login',
  data () {
    return {
      email: '',
      password: '',
      errors: []
    }
  },
  methods: {
    login: function () {
      let e = []
      firebase.auth().signInWithEmailAndPassword(this.email, this.password).then(function (user) {
        location.reload()
      }).catch(function (error) {
        e.push(error.message)
      })
      this.errors = e
    }
  }
}
</script>

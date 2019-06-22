<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md8>
        <v-text-field v-model="userName" label="User Name" outline></v-text-field>
        <v-text-field v-model="userEmail" label="User Email" outline></v-text-field>
        <v-text-field v-model="userPhone" label="Mobile No" outline></v-text-field>
        <v-text-field v-model="userNewPassword" label="New Password" type="password" outline></v-text-field>
        <v-textarea v-model="userBio" label="Bio" outline></v-textarea>
        <input v-show="false" ref="updateAvatar" type="file" accept="image/*" @change="changeAvatar">
        <v-btn color="success" @click="updateUserInfo">
          Update
        </v-btn>
      </v-flex>
      <v-flex xs12 md4>
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-avatar slot="offset" class="mx-auto d-block" size="130" v-on="on">
              <img :src="userProfilePhotoURL" @click="$refs.updateAvatar.click()">
            </v-avatar>
          </template>
          <span>Click to change profile image</span>
        </v-tooltip>
        <v-card-text class="text-xs-center">
          <h6 class="category text-gray font-weight-thin mb-3">Admin</h6>
          <h4 class="card-title font-weight-light">{{ userName }}</h4>
          <p class="card-description font-weight-light mt-2">{{ userBio }}</p>
        </v-card-text>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import firebase from 'firebase'
export default {
  name: 'Profile',
  data () {
    return {
      userName: '',
      userEmail: '',
      userBio: '',
      userPhone: '',
      userProfilePhotoURL: '',
      userNewPassword: ''
    }
  },
  mounted () {
    let userId = firebase.auth().currentUser.uid
    firebase.database().ref().child('Users').child(userId).once('value').then(function (snapshot) {
      this.userName = snapshot.val().name
      this.userEmail = snapshot.val().email
      this.userBio = snapshot.val().bio
      this.userPhone = snapshot.val().phoneNumber
      this.userProfilePhotoURL = snapshot.val().profilePhotoURL
    }.bind(this))
  },
  methods: {
    changeAvatar: function () {
      let fileRef = this.$refs.updateAvatar.files[0]
      let originalLocation = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15)
      let blob = new Blob([ fileRef ], { type: fileRef.type })
      let prevOrigin = ''
      firebase.database().ref().child('Users').child(firebase.auth().currentUser.uid).once('value').then(function (originSnapshot) {
        prevOrigin = originSnapshot.val().origin
        firebase.storage().ref().child('Profile_Images').child(originalLocation).put(blob).then(function (snapshot) {
          snapshot.ref.getDownloadURL().then(function (downloadURL) {
            firebase.database().ref().child('Users').child(firebase.auth().currentUser.uid).update({
              profilePhotoURL: downloadURL,
              origin: originalLocation
            }).then(function () {
              if (prevOrigin !== '-1') firebase.storage().ref().child('Profile_Images').child(prevOrigin).delete()
              location.reload()
            }).catch(function () {
              firebase.storage().ref().child('Profile_Images').child(originalLocation).delete().catch(function (error) {
                console.log(error.message)
              })
            })
          })
        })
      })
    },
    updateUserInfo: function () {
      let user = firebase.auth().currentUser
      if (this.userEmail !== user.email) {
        user.updateEmail(this.userEmail)
        user.sendEmailVerification()
        confirm('Email verification message sent')
      }
      if (this.userNewPassword !== '') {
        user.updatePassword(this.userNewPassword)
      }
      firebase.database().ref().child('Users').child(user.uid).update({
        bio: this.userBio,
        email: this.userEmail,
        name: this.userName,
        phoneNumber: this.userPhone
      })
    }
  }
}
</script>

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import AuthApp from './AuthApp'
import router from './router'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@fortawesome/fontawesome-free/css/all.css'

Vue.use(Vuetify, {
  iconfont: 'fa'
})

Vue.config.productionTip = false

var firebase = require('firebase')

// Your web app's Firebase configuration
var firebaseConfig = {
  apiKey: 'AIzaSyD2TUxl6fvCkMdyIeRwDrMdueZIguJ1qAg',
  authDomain: 'cshare-9ab86.firebaseapp.com',
  databaseURL: 'https://cshare-9ab86.firebaseio.com',
  projectId: 'cshare-9ab86',
  storageBucket: 'cshare-9ab86.appspot.com',
  messagingSenderId: '753739327059',
  appId: '1:753739327059:web:16a198a3e250195d'
}
// Initialize Firebase
firebase.initializeApp(firebaseConfig)

/* eslint-disable no-new */
firebase.auth().onAuthStateChanged(function (user) {
  if (user) {
    new Vue({
      el: '#app',
      router,
      components: { App },
      template: '<App/>'
    })
  } else {
    new Vue({
      el: '#app',
      router,
      components: { AuthApp },
      template: '<AuthApp/>'
    })
  }
})

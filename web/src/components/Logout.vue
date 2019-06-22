<template>
  <div>Logging out</div>
</template>

<script>
import firebase from 'firebase'
export default {
  mounted () {
    firebase.auth().signOut().then(function () {
      location.reload()
    })
  }
}
</script>

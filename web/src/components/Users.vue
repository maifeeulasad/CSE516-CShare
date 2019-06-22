<template>
  <div>
    <v-toolbar flat color="white">
      <v-toolbar-title>Users</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">Edit User</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.name" label="Name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.email" label="Email" readonly="readonly"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-select :items="status" v-model="editedItem.admin" label="Admin Status"></v-select>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.phone" label="Phone Number"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.totalUpVote" label="Total Up Vote"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.totalDownVote" label="Total Down Vote"></v-text-field>
                </v-flex>
              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="red" flat @click="close">Cancel</v-btn>
            <v-btn color="blue darken-1" flat @click="save">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-toolbar>
    <v-data-table :headers="headers" :items="users" class="elevation-1">
      <template v-slot:items="props">
        <td>{{ props.item.uid }}</td>
        <td class="text-xs-center">{{ props.item.name }}</td>
        <td class="text-xs-center">{{ props.item.email }}</td>
        <td class="text-xs-center">{{ props.item.admin }}</td>
        <td class="text-xs-center">{{ props.item.phone }}</td>
        <td class="text-xs-center">{{ props.item.totalUpVote }}</td>
        <td class="text-xs-center">{{ props.item.totalDownVote }}</td>
        <td class="text-xs-center align-center">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon small class="mr-2" @click="editUser(props.item)" v-on="on">
                fas fa-pen
              </v-icon>
            </template>
            <span>Edit user</span>
          </v-tooltip>
        </td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import firebase from 'firebase'
export default {
  name: 'Users',
  data: () => ({
    dialog: false,
    headers: [
      { text: 'User ID', align: 'left', sortable: false, value: 'user id' },
      { text: 'Name', align: 'center', value: 'name' },
      { text: 'Email', align: 'center', value: 'email' },
      { text: 'Admin Status', align: 'center', sortable: false, value: 'admin admin' },
      { text: 'Phone Number', align: 'center', sortable: false, value: 'phone no' },
      { text: 'Total up vote', align: 'center', value: 'up votes' },
      { text: 'Total down vote', align: 'center', value: 'down votes' },
      { text: 'Actions', align: 'center', value: 'action', sortable: false }
    ],
    status: [ 'User', 'Admin' ],
    users: [],
    editedIndex: -1,
    editedItem: {
      uid: '',
      name: '',
      email: '',
      admin: '',
      phone: '',
      totalUpVote: '',
      totalDownVote: ''
    },
    defaultItem: {
      uid: '',
      name: '',
      email: '',
      admin: '',
      phone: '',
      totalUpVote: '',
      totalDownVote: ''
    }
  }),

  watch: {
    dialog (val) {
      val || this.close()
    }
  },

  created () {
    this.initialize()
  },

  methods: {
    initialize () {
      let query = firebase.database().ref().child('Users').orderByKey()
      query.on('value', function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
          this.users.push({
            uid: childSnapshot.key,
            name: childSnapshot.val().name,
            email: childSnapshot.val().email,
            admin: (childSnapshot.val().isAdmin ? 'Admin' : 'User'),
            phone: (childSnapshot.val().phoneNumber ? childSnapshot.val().phoneNumber : 'Not provided'),
            totalUpVote: childSnapshot.val().totalUpVote,
            totalDownVote: childSnapshot.val().totalDownVote
          })
        }.bind(this))
      }.bind(this), function (error) {
        console.log(error.message)
      })
    },

    editUser (item) {
      this.editedIndex = this.users.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    close () {
      this.dialog = false
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },

    save () {
      if (this.editedItem.admin === 'Admin') this.editedItem.admin = true
      else this.editedItem.admin = false
      let userId = this.users[this.editedIndex].uid
      let newData = this.users
      this.users = []
      firebase.database().ref().child('Users').child(userId).update({
        name: this.editedItem.name,
        isAdmin: this.editedItem.admin,
        phoneNumber: this.editedItem.phone,
        totalUpVote: this.editedItem.totalUpVote,
        totalDownVote: this.editedItem.totalDownVote
      }).catch(function () {
        this.users = newData
      }.bind(this))
      if (this.editedItem.admin === true) this.editedItem.admin = 'Admin'
      else this.editedItem.admin = 'User'
      this.close()
    }
  }
}
</script>

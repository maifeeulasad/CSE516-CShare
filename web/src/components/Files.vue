<template>
  <div>
    <v-toolbar flat color="white">
      <v-toolbar-title>Files</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
      <v-spacer></v-spacer>
      <v-dialog v-model="new_dialog" max-width="500px">
        <template v-slot:activator="{ on }">
          <v-btn color="primary" dark class="mb-2" v-on="on">New File</v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">New File</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.name" label="Name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-btn color="warning" @click="$refs.inputUpload.click()">Upload File</v-btn>
                  <input v-show="false" ref="inputUpload" type="file" >
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

      <v-dialog v-model="edit_dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="headline">Edit File</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout wrap>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.name" label="Name"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.type" label="Type" readonly="readonly"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.link" label="File link" readonly="readonly"></v-text-field>
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
    <v-data-table :headers="headers" :items="files" class="elevation-1">
      <template v-slot:items="props">
        <td>{{ props.item.fileId }}</td>
        <td class="text-xs-center">{{ props.item.name }}</td>
        <td class="text-xs-center">{{ props.item.type }}</td>
        <td class="text-xs-center"><a :href="props.item.link" target="_blank">{{ props.item.link }}</a></td>
        <td class="text-xs-center">{{ props.item.uploader }}</td>
        <td class="text-xs-center">{{ props.item.totalUpVote }}</td>
        <td class="text-xs-center">{{ props.item.totalDownVote }}</td>
        <td class="align-center justify-space-around layout px-0">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon small class="mr-2" @click="editFile(props.item)" v-on="on">
                fas fa-pen
              </v-icon>
            </template>
            <span>Edit file</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon small class="mr-2" @click="deleteFile(props.item)" v-on="on">
                fas fa-trash
              </v-icon>
            </template>
            <span>Delete File</span>
          </v-tooltip>
        </td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import firebase from 'firebase'
export default {
  name: 'Files',
  data: () => ({
    new_dialog: false,
    edit_dialog: false,
    headers: [
      { text: 'File ID', align: 'left', sortable: false, value: 'file id' },
      { text: 'Name', align: 'center', value: 'name' },
      { text: 'Type', align: 'center', sortable: false, value: 'type' },
      { text: 'Link', align: 'center', sortable: false, value: 'file location' },
      { text: 'Uploader Name', align: 'center', value: 'uploader' },
      { text: 'Total up vote', align: 'center', value: 'up votes' },
      { text: 'Total down vote', align: 'center', value: 'down votes' },
      { text: 'Actions', align: 'center', value: 'action', sortable: false }
    ],
    files: [],
    editedIndex: -1,
    editedItem: {
      fileId: '',
      name: '',
      type: '',
      link: '',
      uploader: '',
      totalUpVote: '',
      totalDownVote: ''
    },
    defaultItem: {
      fileId: '',
      name: '',
      type: '',
      link: '',
      uploader: '',
      totalUpVote: '',
      totalDownVote: ''
    }
  }),

  watch: {
    new_dialog (val) {
      val || this.close()
    },

    edit_dialog (val) {
      val || this.close()
    }
  },

  created () {
    this.initialize()
  },

  methods: {
    initialize () {
      let query = firebase.database().ref().child('Files').orderByKey()
      query.on('value', function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
          let uploaderUID = childSnapshot.val().uploaderUID
          let uploaderName = ''
          firebase.database().ref().child('Users').child(uploaderUID).once('value').then(function (snapshot) {
            uploaderName = snapshot.val().name
            this.files.push({
              fileId: childSnapshot.key,
              name: childSnapshot.val().name,
              type: childSnapshot.val().type,
              link: childSnapshot.val().link,
              uploader: uploaderName,
              totalUpVote: childSnapshot.val().totalUpVote,
              totalDownVote: childSnapshot.val().totalDownVote
            })
          }.bind(this))
        }.bind(this))
      }.bind(this), function (error) {
        console.log(error.message)
      })
    },

    editFile (item) {
      this.editedIndex = this.files.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.edit_dialog = true
    },

    deleteFile (item) {
      let newData = this.files
      this.files = []
      let originalLocation = ''
      if (confirm('Are you sure you want to delete this file?')) {
        firebase.database().ref().child('Files').child(item.fileId).once('value').then(function (snapshot) {
          originalLocation = snapshot.val().origin
          firebase.storage().ref().child('Files').child(originalLocation).delete().then(function () {
            firebase.database().ref().child('Files').child(item.fileId).remove().catch(function (error) {
              console.log(error.message)
            })
          }).catch(function () {
            this.files = newData
          })
        }).catch(function () {
          this.files = newData
        })
      }
    },

    close () {
      this.new_dialog = false
      this.edit_dialog = false
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },

    save () {
      let newData = this.files
      this.files = []
      if (this.editedIndex > -1) {
        let fileId = newData[this.editedIndex].fileId
        firebase.database().ref().child('Files').child(fileId).update({
          name: this.editedItem.name,
          totalUpVote: this.editedItem.totalUpVote,
          totalDownVote: this.editedItem.totalDownVote
        }).catch(function () {
          this.files = newData
        }.bind(this))
      } else {
        let fileRef = this.$refs.inputUpload.files[0]
        let fileName = this.editedItem.name
        let originalLocation = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15)
        let blob = new Blob([ fileRef ], { type: fileRef.type })
        firebase.storage().ref().child('Files').child(originalLocation).put(blob).then(function (snapshot) {
          snapshot.ref.getDownloadURL().then(function (downloadURL) {
            firebase.database().ref().child('Files').push({
              name: fileName,
              type: fileRef.type,
              link: downloadURL,
              origin: originalLocation,
              uploaderUID: firebase.auth().currentUser.uid,
              totalUpVote: 0,
              totalDownVote: 0
            }).catch(function () {
              firebase.storage().ref().child('Files').child(originalLocation).delete().catch(function (error) {
                console.log(error.message)
              })
              this.files = newData
            })
          }).catch(function () {
            this.files = newData
          })
        }).catch(function () {
          this.files = newData
        })
      }
      this.close()
    }
  }
}
</script>

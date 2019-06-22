<template>
  <div>
    <v-toolbar flat color="white">
      <v-toolbar-title>Files</v-toolbar-title>
      <v-divider class="mx-2" inset vertical></v-divider>
    </v-toolbar>
    <v-data-table :headers="headers" :items="reports" class="elevation-1">
      <template v-slot:items="props">
        <td>{{ props.item.reportId }}</td>
        <td class="text-xs-center">{{ props.item.fileId }}</td>
        <td class="text-xs-center">{{ props.item.fileName }}</td>
        <td class="text-xs-center">{{ props.item.reporter }}</td>
        <td class="text-xs-center">{{ props.item.description }}</td>
        <td class="align-center text-xs-center">
          <v-tooltip bottom>
            <template v-slot:activator="{ on }">
              <v-icon small class="mr-2" @click="deleteFile(props.item)" v-on="on">
                fas fa-trash
              </v-icon>
            </template>
            <span>Delete Report</span>
          </v-tooltip>
        </td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import firebase from 'firebase'
export default {
  name: 'Reports',
  data: () => ({
    headers: [
      { text: 'Report ID', align: 'left', sortable: false, value: 'report id' },
      { text: 'File ID', align: 'center', sortable: false, value: 'file id' },
      { text: 'File Name', align: 'center', value: 'file name' },
      { text: 'Reporter Name', align: 'center', value: 'reporter name' },
      { text: 'Description', align: 'center', sortable: false, value: 'description' },
      { text: 'Actions', align: 'center', value: 'action', sortable: false }
    ],
    reports: []
  }),

  created () {
    this.initialize()
  },

  methods: {
    initialize () {
      let query = firebase.database().ref().child('Reports').orderByKey()
      query.on('value', function (snapshot) {
        snapshot.forEach(function (childSnapshot) {
          let reporterUID = childSnapshot.val().reporterID
          let fileID = childSnapshot.val().fileID
          let reporterName = ''
          let fileName = ''
          firebase.database().ref().child('Users').child(reporterUID).once('value').then(function (userSnapshot) {
            reporterName = userSnapshot.val().name
            firebase.database().ref().child('Files').child(fileID).once('value').then(function (fileSnapshot) {
              fileName = fileSnapshot.val().name
              this.reports.push({
                reportId: childSnapshot.key,
                fileId: fileID,
                fileName: fileName,
                reporter: reporterName,
                description: childSnapshot.val().description
              })
            }.bind(this))
          }.bind(this))
        }.bind(this))
      }.bind(this), function (error) {
        console.log(error.message)
      })
    },

    deleteFile (item) {
      let newData = this.reports
      this.reports = []
      if (confirm('Are you sure you want to delete this file?')) {
        let reportID = item.reportId
        firebase.database().ref().child('Reports').child(reportID).remove().catch(function () {
          this.reports = newData
        })
      }
    }
  }
}
</script>

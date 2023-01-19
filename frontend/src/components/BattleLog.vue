<template>
  <v-table>
    <thead>
    <tr>
      <th class="text-center">
        Id
      </th>
      <th>
        Fight Date
      </th>
      <th>
        Winner
      </th>
      <th>
        Loser
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
      v-for="item in items"
      :key="item.name"
    >
      <td>{{ item.id }}</td>
      <td>{{ new Date(item.fightDate * 1000).toUTCString() }}</td>
      <td>{{ item.winnerName }}</td>
      <td>{{ item.loserName }}</td>
    </tr>
    </tbody>
  </v-table>
</template>

<script>
import URL_CONSTANTS from "@/URL_CONSTANTS";
import axios from "axios";

export default {
  name: "BattleLog",
  data() {
    return {
      items: [],
      benched: 0,
    }
  },
  created() {
    this.getBattleLog()
  },
  methods: {
    getBattleLog() {
      axios.get(URL_CONSTANTS.FIGHT_MICROSERVICE_GET_BATTLES_LOG).then(resp => {
        let d = resp.data
        this.items = d.splice(d.length - 20).reverse()
        console.log(this.items)
      }).catch(e => console.log(e))
    }
  }
}
</script>

<style scoped>

</style>

<template>
  <div class="content">
    <header class="header">
      <h1 class="px-3">Welcome to Super Heroes Fight!</h1>
    </header>
    <v-container class="panel_container">
      <entity-panel
        class="entity_panel"
        type="hero"
        ref="hero_entity"
        :entity="fighters?.hero"
      ></entity-panel>
      <fight-control-panel
        class="control_panel"
        @swapSignal="getNewFighters"
        @fightSignal="fight"
      ></fight-control-panel>
      <entity-panel
        class="entity_panel"
        type="villain"
        ref="villain_entity"
        :entity="fighters?.villain"
      ></entity-panel>
    </v-container>

    <v-btn @click="KeycloakService.logout()" class="logout-btn">Logout</v-btn>
  </div>
</template>

<script>
import HeroPanel from "@/components/EntityPanel.vue";
import EntityPanel from "@/components/EntityPanel.vue";
import FightControlPanel from "@/components/FightControlPanel.vue";
import Stats from "@/components/BattleLog.vue";
import axios from "axios";
import URL_CONSTANTS from "@/const/URL_CONSTANTS";
import KeycloakService from "@/plugins/KeycloakService";

export default {
  name: "Main",
  computed: {
    KeycloakService() {
      return KeycloakService
    }
  },
  components: {Stats, FightControlPanel, EntityPanel, HeroPanel},
  data() {
    return {
      fighters: null,
    }
  },
  mounted() {
    this.getFighters()
  },
  methods: {
    getFighters() {
      axios.get(URL_CONSTANTS.FIGHT_MICROSERVICE_GET_RANDOM).then(resp => {
        this.fighters = resp.data
      }).catch(e => console.log(e))
    },
    getWinner() {
      axios.post(URL_CONSTANTS.FIGHT_MICROSERVICE_POST, this.fighters).then(resp => {
        if (resp.data.winnerTeam === "heroes") {
          this.fighters.hero.win = true
          this.fighters.villain.win = false
        } else {
          this.fighters.hero.win = false
          this.fighters.villain.win = true
        }
      }).catch(e => console.log(e))
    },
    fight() {
      this.getWinner()
    },
    getNewFighters() {
      this.getFighters()
    }
  },
}
</script>

<style scoped>
.content {
  padding: 0 5%;
}

.header {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-bottom: 1px darkgrey solid;
}

.panel_container {
  display: flex;
  justify-content: space-evenly;
  flex-direction: row;
  flex-wrap: wrap;
}

.entity_panel {
  flex-basis: 30%;
}

.logout-btn {
  position: absolute;
  width: 150px;
  height: 50px;
  right: 30px;
  bottom: 40px;
}

@media screen and (max-width: 790px) {
  .content {
    margin: 0 0;
  }

  .control_panel {
    order: -1;
    flex-basis: 100%;
  }

  .entity_panel {
    flex-basis: 40%;
  }
}
</style>

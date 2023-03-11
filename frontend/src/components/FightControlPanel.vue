<template>
  <div class="panel">
    <v-btn class="btn" @click="$emit('fightSignal')">
      <v-icon>mdi-sword-cross</v-icon>
      <span>Fight</span>
    </v-btn>
    <v-btn class="btn" @click="$emit('swapSignal')">
      <v-icon>mdi-swap-horizontal</v-icon>
      <span>New fighters</span>
    </v-btn>
    <v-dialog
      v-model="statsDialog"
      width="1000"
    >
      <template v-slot:activator>
        <v-btn :disabled="!KeycloakService.checkRole('APP_ADMIN')" class="btn" @click="statsDialog = !statsDialog">
          <v-icon>mdi-view-list</v-icon>
          <span>Statistics</span>
        </v-btn>
      </template>
      <v-card>
        <v-card-text>
          <stats></stats>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            text
            @click="statsDialog = false"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import Stats from "@/components/BattleLog.vue";
import KeycloakService from "@/plugins/KeycloakService";

export default {
  name: "FightControlPanel",
  computed: {
    KeycloakService() {
      return KeycloakService
    }
  },
  components: {Stats},
  data() {
    return {
      statsDialog: false
    }
  }
}
</script>

<style scoped>
.btn {
  width: 100%;
  margin-bottom: 5px;
}

.panel {
  display: flex;
  flex-direction: column;
  margin: 5px 15px;
}
</style>

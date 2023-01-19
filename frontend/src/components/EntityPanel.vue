<template>
  <v-card :class="['entity_panel_container',
  {'win_hero': reactiveEntity?.win && type === 'hero'},
  {'win_villain': reactiveEntity?.win && type === 'villain'}]">
    <div class="entity_header">
      <h3>
        {{ reactiveEntity?.name }}
      </h3>
    </div>
    <v-img
      ref="img"
      :src="reactiveEntity?.picture"
      @load="setClip"></v-img>
    <v-divider class="my-2"></v-divider>
    <div class="level_container">
      <v-icon>
        mdi-lightning-bolt
      </v-icon>
      <span>
      {{ reactiveEntity?.level }}
    </span>
    </div>
  </v-card>
</template>

<script>
import {toRef} from "vue";

export default {
  name: "EntityPanel",
  props: {
    entity: Object,
    type: String
  },
  setup(props) {
    const reactiveEntity = toRef(props, "entity")
    return {reactiveEntity}
  },
  methods: {
    setClip() {
      this.$nextTick(
        () => {
          let a = this.$refs.img.$el.children[1]
          a.style.clipPath = "polygon(15% 0, 100% 0, 90% 40%, 100% 40%, 85% 100%, 0% 100%, 10% 60%, 0% 60%, 20% 0%)"
        }
      )
    },
    refresh() {

    }
  }
}
</script>

<style scoped>
img {
  clip-path: polygon(15% 0, 100% 0, 90% 40%, 100% 40%, 85% 100%, 0% 100%, 10% 60%, 0% 60%, 20% 0%);
}

h3 {
  text-align: center;
}

.entity_header {
  display: flex;
  height: 50px;
  justify-content: center;
  align-items: center
}

.entity_panel_container {
  display: flex;
  flex-direction: column;
  margin-top: 5px;
  padding: 10px;
}

.level_container {
  display: flex;
  flex-direction: row;
  justify-content: center;
}

.win_container {
  position: absolute;
  width: 100%;
  height: 100%;
}

.win_hero {
  box-shadow: 0 0 7px 7px #50e5ff,
  0 0 2px 2px #fff inset,
  0 0 7px 7px #1dddff inset;
}

.win_villain {
  box-shadow: 0 0 7px 7px #ff5050,
  0 0 2px 2px #fff inset,
  0 0 7px 7px #ff1d1d inset;
}

</style>
